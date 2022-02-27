import language.line._
import backend.LineBackend

val prog: Stm =
  CompoundStm(AssignStm("a",
    OpExp(NumExp(5),
      Plus(), NumExp(3))),
    CompoundStm(AssignStm("b",
      EseqExp(PrintStm(PairExpList(IdExp("a"),
        LastExpList(OpExp(IdExp("a"), Minus(),NumExp(1))))),
        OpExp(NumExp(10), Times(), IdExp("a")))),
        PrintStm(LastExpList(IdExp("b")))));

println((new LineBackend()).maxArgs(prog))
(new LineBackend()).interpret(prog)
