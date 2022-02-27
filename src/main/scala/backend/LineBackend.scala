package backend

import language.line._

import scala.collection.immutable.HashMap

class LineBackend {
  def maxArgs(begin: Exp): Int = {
    begin match {
      case PrintStm(par) => maxArgs(par)
      case OpExp(l, _, r) => List(maxArgs(l), maxArgs(r)).max
      case EseqExp(s, e) => List(maxArgs(s), maxArgs(e)).max
      case CompoundStm(c, n) => List(maxArgs(c), maxArgs(n)).max
      case AssignStm(_, e) => maxArgs(e)
      case x: ExpList => pairSize(x)
      case _ => 0
    }
  }

  private def pairSize(list: ExpList): Int = {
    list match {
      case PairExpList(e, es) => pairSize(es) + 1
      case LastExpList(_) => 1
    }
  }

  def interpret(begin: Exp): Exp = {
    interpretRec(begin, HashMap.empty)._2
  }

  def interpretRec(begin: Exp, values: HashMap[String, Int]): (HashMap[String, Int], Exp) = {
    begin match {
      case NumExp(x) => (values, NumExp(x))
      case OpExp(l, op, r) => {
        val (_, xExp) = interpretRec(l, values)
        val (_, yExp) = interpretRec(r, values)
        val x = xExp.asInstanceOf[NumExp]
        val y = yExp.asInstanceOf[NumExp]
        op match {
          case Plus() => (values, NumExp(x.num + y.num))
          case Minus() => (values, NumExp(x.num - y.num))
          case Times() => (values, NumExp(x.num * y.num))
          case Div() => (values, NumExp(x.num / y.num))
        }
      }
      case EseqExp(s, e) => {
        val (v2, _) = interpretRec(s, values)
        interpretRec(e, v2)
      }
      case CompoundStm(c, n) => {
        val (v2, _) = interpretRec(c, values)
        interpretRec(n, v2)
      }
      case AssignStm(id: String, e) => {
        val (v2: HashMap[String, Int], r2: NumExp) = interpretRec(e, values)
        (values + (id -> r2.num), r2)
      }
      case IdExp(id: String) => {
        (values, NumExp(values.getOrElse(id, 0)))
      }
      case p: PairExpList => {
        val (v2: HashMap[String, Int], r: NumExp) = interpretRec(p.e, values)
        interpretRec(p.es, values)
      }
      case p: LastExpList => {
        val (v2: HashMap[String, Int], r: NumExp) = interpretRec(p.e, values)
        (values, r)
      }
      case PrintStm(par) => {
        val res = printListRes(values, par, List.empty).reverse
        println(res)
        (values, NumExp(res.last)) //A print statement should not have to return 0
      }
    }
  }

  private def printListRes(values: HashMap[String, Int], exp: ExpList, acc: List[Int]): List[Int] = {
    exp match {
      case PairExpList(e, es) => {
        val (_, r: NumExp) = interpretRec(e, values)
        printListRes(values, es, acc.prepended(r.num))
      }
      case LastExpList(e) => {
        val (_, r: NumExp) = interpretRec(e, values)
        acc.prepended(r.num)
      }
    }
  }
}
