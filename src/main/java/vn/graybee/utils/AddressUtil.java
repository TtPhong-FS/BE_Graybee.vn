package vn.graybee.utils;

public class AddressUtil {

    public static String formatFullAddress(String streetAddress, String commune, String district, String city) {
        StringBuilder sb = new StringBuilder();

        if (streetAddress != null && !streetAddress.isEmpty()) {
            sb.append(streetAddress).append(", ");
        }
        if (commune != null && !commune.isEmpty()) {
            sb.append(commune).append(", ");
        }
        if (district != null && !district.isEmpty()) {
            sb.append(district).append(", ");
        }
        if (city != null && !city.isEmpty()) {
            sb.append(city);
        }

        String result = sb.toString().trim();
        if (result.endsWith(",")) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

}
