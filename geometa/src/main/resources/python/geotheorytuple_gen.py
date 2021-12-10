if __name__ == '__main__':
    for i in range(1, 23):
        print('trait GTTuple{}['.format(i))
        print('  T <: GeomTheory,')
        for j in range(1, i + 1):
            print('  A{} <: GTBaseType[T],'.format(j))
        print('  Tup <: Tuple{}['.format(i))
        for j in range(1, i):
            print('    A{},'.format(j))
        print('    A{}'.format(i))
        print('  ]')
        print('] extends GTBaseType[T] {}')
        print()
