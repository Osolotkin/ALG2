public class Shapes {

    public static void main(String [] args) {

        Shape [] sh = {new Circle(3), new Square(3), new Rectangle(3,4)};

        print(sh);
        System.out.println();

        sortLowHigh(sh);
        print(sh);
        System.out.println();

        sortHighLow(sh);
        System.out.println();
        print(sh);

    }

    private static void sortLowHigh(Shape[] array) {
        for(int i = 0; i < array.length; i++) {
            for(int j = 1; j < array.length; j++) {
                if(array[j-1].isAreaBigger(array[j])){
                    Shape tmp = array[j];
                    array[j] = array[j-1];
                    array[j-1] = tmp;
                }
            }
        }
    }

    private static void sortHighLow(Shape[] array) {
        for(int i = 0; i < array.length; i++) {
            for(int j = 1; j < array.length; j++) {
                if(!array[j-1].isAreaBigger(array[j])){
                    Shape tmp = array[j];
                    array[j] = array[j-1];
                    array[j-1] = tmp;
                }
            }
        }
    }

    private static void print(Shape [] sh) {
        for (int i = 0; i < sh.length; i++)
            System.out.println(sh[i].getName() + ": " + sh[i].area());
    }

}
