import java.io.*;
import java.util.*;
import java.math.BigInteger;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Main {
    public static BigInteger convertToDecimal(String value, int base) {
        return new BigInteger(value, base);
    }
    public static BigInteger computeConstantTerm(double[] x, BigInteger[] y, int k) {
        BigInteger C = BigInteger.ZERO;
        for (int i = 0; i < k; i++) {

            double Li_at_0 = 1.0;

            for (int j = 0; j < k; j++) {
                if (i != j) {
                    Li_at_0 *= (0 - x[j]) / (x[i] - x[j]);
                }
            }
            BigInteger term = y[i].multiply(BigInteger.valueOf((long) Li_at_0));

            C = C.add(term);
        }

        return C;
    }
    public static void main(String[] args) throws Exception {
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(new FileReader("input.json"));
        JSONObject keys = (JSONObject) obj.get("keys");
        int n = Integer.parseInt(keys.get("n").toString());
        int k = Integer.parseInt(keys.get("k").toString());
        double[] x = new double[k];
        BigInteger[] y = new BigInteger[k];
        int index = 0;
        for (Object key : obj.keySet()) {

            String s = key.toString();
            if (s.equals("keys")) continue;

            if (index < k) {
                double xi = Double.parseDouble(s);
                JSONObject node = (JSONObject) obj.get(s);
                int base = Integer.parseInt(node.get("base").toString());
                String value = node.get("value").toString();
                BigInteger yi = convertToDecimal(value, base);
                x[index] = xi;
                y[index] = yi;
                index++;
            }
        }
        BigInteger C = computeConstantTerm(x, y, k);
        System.out.println("\nConstant term C = " + C);
    }
}


