package util;

public class NewId {
    public static String getNewId(String lastId) {
        String prefix = lastId.replaceAll("[0-9]", "");
        int number = Integer.parseInt(lastId.replaceAll("\\D", ""));
        number++;
        String newNumber = String.format("%0" + (lastId.length() - prefix.length()) + "d", number);
        return prefix + newNumber;
    }
}
