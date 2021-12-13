if __name__ == '__main__':
    for name in ['and', 'or']:
        for i in range(2, 23):
            print('  implicit def {}['.format(name))
            print('    T <: GeomTheory,')
            for j in range(1, i):
                print('    F{} <: GTFormula[T],'.format(j))
            print('    F{} <: GTFormula[T]'.format(i))
            print('  ](')
            print('    theory: T,')
            for j in range(1, i):
                print('    formula{}: F{},'.format(j, j))
            print('    formula{}: F{}'.format(i, i))
            print('  ): GTFormula[T] = macro GeometricAndBundle.{}{}['
                  .format(name, i))
            print('      T,')
            for j in range(1, i):
                print('      F{},'.format(j))
            print('      F{}'.format(i))
            print('    ]')
            print()
