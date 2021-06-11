import javax.lang.model.element.NestingKind;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static String fileName = "file.txt";
    static int indexList = 1;
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    abstract static class Shop {
        String name;
        String address;

        abstract void allBuyers();
        abstract void minBuyers();
        abstract void commentWithWord();

    }

    public static class Time extends Shop {
        int buyers;
        String comment;

        @Override
        public void allBuyers() {
            int all = 0;
            for(Time tmp : timeInfo)
            {
                all+= tmp.buyers;
            }
            System.out.println("Загальна к-сть покупців: " + all);
        }

        @Override
        public void minBuyers() {
            Time min = timeInfo.get(0);
            for(Time tmp : timeInfo)
            {
                if(min.buyers > tmp.buyers) min = tmp;
            }
            System.out.println("Мінімальна к-сть покупців: " + min.buyers);
        }

        @Override
        public void commentWithWord() {
            String word;
            System.out.println("Введіть слово: ");
            word = scanner.nextLine();

            for(Time tmp : timeInfo)
            {
                if(tmp.comment.contains(word)) {
                    System.out.println("Коментар який містить слово: " + tmp.comment);

                }
            }
        }

        public void AddNewData() {
            try {
                FileOutputStream fos = new FileOutputStream(fileName, true);
                DataOutputStream dos = new DataOutputStream(fos);
                System.out.print("Введіть назву: ");
                name = scanner.nextLine();
                System.out.print("Введіть адрес: ");
                address = scanner.nextLine();
                System.out.print("Введіть к-сть покупців: ");
                buyers = scanner.nextInt();
                System.out.print("Введіть коментар: ");
                comment = scanner.nextLine();
                comment = scanner.nextLine();
                dos.writeUTF(name);
                dos.writeUTF(address);
                dos.write(buyers);
                dos.writeUTF(comment);
                dos.close();
                timeInfo.add(this);
            } catch (IOException e) {
                System.out.println("Error");
            }
        }

        public void EditData() {
            byte lineIndex;
            do {
                do {
                    System.out.print("Введіть поле дял редагування\n(1.Назва | 2.Адрес | 3.К-сть покупців | 4.Коментар)\n(5.Назад в меню)\nВведіть індекс:");
                    lineIndex = scanner.nextByte();
                } while (lineIndex < 1 || lineIndex > 6);
                switch (lineIndex) {
                    case 1: {
                        System.out.print("Введіть прізвище: ");
                        name = scanner.nextLine();
                        name = scanner.nextLine();
                    }

                    case 2: {
                        System.out.print("Введіть адрес: ");
                        address = scanner.nextLine();
                        address = scanner.nextLine();
                    }
                    break;

                    case 3: {
                        System.out.print("Введіть к-сть покупців: ");
                        buyers = scanner.nextByte();
                    }
                    break;
                    case 4: {
                        System.out.print("Введіть коментар: ");
                        comment = scanner.nextLine();
                        comment = scanner.nextLine();

                    }
                    break;


                }
            } while (lineIndex != 5);
            try {
                FileOutputStream tmpfos = new FileOutputStream(fileName);
                DataOutputStream tmpdos = new DataOutputStream(tmpfos);
                tmpdos.close();
                FileOutputStream fos = new FileOutputStream(fileName, true);
                DataOutputStream dos = new DataOutputStream(fos);
                for (Time tmpVisit : timeInfo) {
                    dos.writeUTF(tmpVisit.name);
                    dos.writeUTF(tmpVisit.address);
                    dos.write(tmpVisit.buyers);
                    dos.writeUTF(tmpVisit.comment);

                }
                dos.close();
            } catch (IOException e) {
                System.out.println("Error");
            }
        }

        public void Show() {
            System.out.println("Назва: " + name + " | Адрес: " + address + " | К-сть покупців: " + buyers + " |\n Коментар: " + comment);
        }
    }

    static ArrayList<Time> timeInfo = new ArrayList<Time>();

    static void LoadFromFile() {
        timeInfo.clear();
        try {
            FileInputStream fis = new FileInputStream(fileName);
            DataInputStream dis = new DataInputStream(fis);
            Time temp;
            while (dis.available() > 0) {
                temp = new Time();
                temp.name = dis.readUTF();
                temp.address = dis.readUTF();
                temp.buyers = dis.read();
                temp.comment = dis.readUTF();
                timeInfo.add(temp);
            }
            dis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String enterkey;
        LoadFromFile();
        try {
            do {
                System.out.println("Введіть:\n 'р' - редагувати,\n 'а' - додати,\n 'в' - вивід,\n 'с' - Загальна кількість покупців,\n 'м' - година з найменшою кількістю покупців,\n 'н' - коментарями з певними словами,\n 'д' - вилучення,\n 'е' - вихід");


                enterkey = reader.readLine();


                Time temp = new Time();
                switch (enterkey.charAt(0)) {
                    case 'р': {
                        if (timeInfo.isEmpty()) {
                            System.out.println("Список пустий, додайти дані.");
                        } else {
                            System.out.println("Список:");
                            indexList = 1;
                            for (Time tmpVisit : timeInfo) {
                                System.out.print(indexList + ": ");
                                tmpVisit.Show();
                                indexList++;
                            }
                        }
                        byte index;
                        do {
                            System.out.print("Введіть індекс(" + "from 1 to " + timeInfo.size() + "):");
                            index = scanner.nextByte();
                        } while (index < 1 || index > timeInfo.size());
                        timeInfo.get(index - 1).EditData();
                    }
                    break;
                    case 'в': {
                        if (timeInfo.isEmpty()) {
                            System.out.println("Список пустий, додайте дані.");
                        } else {
                            System.out.println("Список:");
                            indexList = 1;
                            for (Time tmpVisit : timeInfo) {
                                System.out.print(indexList + ": ");
                                tmpVisit.Show();
                                indexList++;
                            }
                        }
                    }
                    break;
                    case 'а':
                        temp.AddNewData();
                        break;
                    case 'с': {
                        if (timeInfo.isEmpty()) {
                            System.out.println("Список пустий, додайти дані.");
                        } else {
                            timeInfo.get(0).allBuyers();
                        }
                    }
                    break;
                    case 'н': {
                        if (timeInfo.isEmpty()) {
                            System.out.println("Список пустий, додайти дані.");
                        } else {
                            timeInfo.get(0).commentWithWord();
                        }
                    }
                    break;
                    case 'м': {
                        if (timeInfo.isEmpty()) {
                            System.out.println("Список пустий, додайти дані.");
                        } else {
                            timeInfo.get(0).minBuyers();
                        }
                    }
                    break;
                    case 'д': {
                        if (timeInfo.isEmpty()) {
                            System.out.println("Список пустий, додайти дані.");
                        } else {
                            System.out.println("Список:");
                            indexList = 1;
                            for (Time tmpVisit : timeInfo) {
                                System.out.print(indexList + ": ");
                                tmpVisit.Show();
                                indexList++;
                            }
                        }
                        byte index;
                        do {
                            System.out.print("Введіть індекс (" + "from 1 to " + timeInfo.size() + "):");
                            index = scanner.nextByte();
                        } while (index < 1 || index > timeInfo.size());

                        timeInfo.remove(index - 1);
                        try {
                            FileOutputStream tmpfos = new FileOutputStream(fileName);
                            DataOutputStream tmpdos = new DataOutputStream(tmpfos);
                            tmpdos.close();
                            FileOutputStream fos = new FileOutputStream(fileName, true);
                            DataOutputStream dos = new DataOutputStream(fos);
                            for (Time tmpVisit : timeInfo) {
                                dos.writeUTF(tmpVisit.name);
                                dos.writeUTF(tmpVisit.address);
                                dos.write(tmpVisit.buyers);
                                dos.writeUTF(tmpVisit.comment);

                            }
                            dos.close();
                        } catch (IOException e) {
                            System.out.println("Error");
                        }
                    }
                    break;
                }
            }
            while (enterkey.charAt(0) != 'е');

        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

}
