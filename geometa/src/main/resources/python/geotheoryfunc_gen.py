if __name__ == '__main__':
    for i in range(22):
        print('trait GTFunc{}['.format(i))
        print('  T <: GeomTheory,')
        for j in range(1, i + 1):
            print('  A{} <: GTBaseType[T],'.format(j))
        print('  R <: GTBaseType[T],')
        print('  Func <: Function{}['.format(i))
        for j in range(1, i + 1):
            print('    A{},'.format(j))
        print('    R')
        print('  ]')
        print('] extends GTTuple{}['.format(i + 1))
        print('  T,')
        for j in range(1, i + 1):
            print('  A{},'.format(j))
        print('  R,')
        print('  Tuple{}['.format(i + 1))
        for j in range(1, i + 1):
            print('    A{},'.format(j))
        print('    R')
        print('  ]')
        print('] with GTFunc[T] {}')
        print()
