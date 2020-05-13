public class HuricaneMain {

    public static void main(String [] args) {
        String path = "src/hurricanedata.txt";
        HuricaneData data = new HuricaneData();
        data.readFile(path);

        //print(data);
        //printInRange(data, 1996, 2000);
        //printCategoryAndSpeed(data, "Alberto");
        printBySpeed(data);


    }

    private static void printBySpeed(HuricaneData data) {
        int tmp [] = data.getWindSpeed().clone();
        int indexes [] = new int[tmp.length];
        for (int i = 0; i < tmp.length; i++) {
            indexes[i] = i;
        }

        for (int i = 0; i < tmp.length; i++) {
            for (int j = 1; j < tmp.length; j++) {
                if (tmp[j] > tmp[j - 1]) {
                    int x = tmp[j];
                    tmp[j] = tmp[j-1];
                    tmp[j-1] = x;
                    x = indexes[j];
                    indexes[j] = indexes[j-1];
                    indexes[j-1] = x;
                }
            }
        }

        for (int i = 0; i < tmp.length; i++) {
            System.out.print(data.getYear()[indexes[i]] + " ");
            System.out.print(data.getMonth()[indexes[i]] + " ");
            System.out.print(data.getPressures()[indexes[i]] + " ");
            System.out.print(data.getWindSpeed()[indexes[i]] + " ");
            System.out.print(data.getName()[indexes[i]]);
            System.out.println();
        }
    }

    private static void printCategoryAndSpeed(HuricaneData data, String name) {
        name = name.trim();
        for (int i = 0; i < data.getYear().length; i++) {
            if (data.getName()[i].equals(name)) {
                System.out.print(data.getName()[i] + " ");
                System.out.print((int)(data.getWindSpeed()[i]/1.852) + "km/h ");
                System.out.print(getCategory(data.getWindSpeed()[i]));
                System.out.println();
                return;
            }
        }
    }

    private static String getCategory(int windSpeed) {

        if (windSpeed <= 95) {
            return "Category 1";
        } else if (windSpeed <= 110) {
            return "Category 2";
        } else if (windSpeed <= 129) {
            return "Category 3";
        } else if (windSpeed <= 156) {
            return "Category 4";
        } else {
            return "Category 5";
        }

    }

    private static void print(HuricaneData data) {
        for (int i = 0; i < data.getYear().length; i++) {
            System.out.print(data.getYear()[i] + " ");
            System.out.print(data.getMonth()[i] + " ");
            System.out.print(data.getPressures()[i] + " ");
            System.out.print(data.getWindSpeed()[i] + " ");
            System.out.print(data.getName()[i]);
            System.out.println();
        }
    }

    private static void printInRange(HuricaneData data, int lwBnd, int upBnd) {

        for (int i = 0; i < data.getYear().length; i++) {
            if (data.getYear()[i] >= lwBnd && data.getYear()[i] <= upBnd) {
                System.out.print(data.getYear()[i] + " ");
                System.out.print(data.getMonth()[i] + " ");
                System.out.print(data.getPressures()[i] + " ");
                System.out.print(data.getWindSpeed()[i] + " ");
                System.out.print(data.getName()[i]);
                System.out.println();
            } else if(data.getYear()[i] > upBnd) {
                return;
            }
        }

    }
}
