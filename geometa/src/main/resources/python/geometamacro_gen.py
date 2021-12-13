if __name__ == '__main__':
  for name in ['and', 'or']:
    for i in range(2, 23):
      print('  def {}{}['.format(name, i))
      print('    T <: GeomTheory : c.WeakTypeTag,')
      for j in range(1, i):
          print('    F{} <: GTFormula[T] : c.WeakTypeTag,'.format(j))
      print('    F{} <: GTFormula[T] : c.WeakTypeTag'.format(i))
      print('  ](')
      print('    theory: c.Expr[T],')
      for j in range(1, i):
          print('    formula{}: c.Expr[F{}],'.format(j, j))
      print('    formula{}: c.Expr[F{}]'.format(i, i))
      print('  ): c.Expr[GTFormula[T]] = {')
      print('    import c.universe._')
      print('    val ttObs = weakTypeOf[GTObservationalClass[T]]')
      print()
      for j in range(1, i + 1):
          print('    val ttF{} = weakTypeOf[F{}]'.format(j, j))
      print()
      print('    val ext =')
      print('      if (')
      for j in range(1, i):
          print('        ttF{} <:< ttObs &&'.format(j))
      print('        ttF{} <:< ttObs'.format(i))
      print('      )')
      print('        ttObs')
      print('      else')
      print('        weakTypeOf[GTFormula[T]]')
      print()
      print('    val anon = newTypeName(c.fresh())')
      print('    c.Expr(')
      print('      q\"\"\"')
      print('       {')
      print('         class $anon extends $ext {}')
      print('         new $anon()')
      print('       }')
      print('       \"\"\"')
      print('    )')
      print('  }')
      print()
