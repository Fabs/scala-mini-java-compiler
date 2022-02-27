import language.line._

val prog: Stm =
  new CompoundStm(new AssignStm("a",
    new OpExp(new NumExp(5),
      Plus(), new NumExp(3))),
    new CompoundStm(new AssignStm("b",
      new EseqExp(new PrintStm(new PairExpList(new IdExp("a"),
        new LastExpList(new OpExp(new IdExp("a"), OpExp.Minus,new NumExp(1))))),
        new OpExp(new NumExp(10), OpExp.Times, new IdExp("a")))),
      new PrintStm(new LastExpList(new IdExp("b")))));
