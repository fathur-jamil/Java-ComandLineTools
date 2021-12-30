package picocli;

import com.fasterxml.jackson.databind.ObjectMapper;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.concurrent.Callable;

@Command(name = "mytools", description = "Performs file manipulation operations", mixinStandardHelpOptions = true, version = "File Client 1.0")
public class FileClient implements Callable<Integer> {

    @Option(names = "-f", description = "From directory file")
    private String directoryFrom;

    @Option(names = "-t", description = "Type to conversion File")
    private String conversionTo;

    @Option(names = "-o", description = "To directory file")
    private String directoryTo;

    public Integer call() throws Exception {
        if (directoryFrom != null && directoryTo != null) {
            try {
                File fileFrom = new File(directoryFrom);
                Scanner scannerFile = new Scanner(fileFrom);

                FileWriter fileWriter = new FileWriter(directoryTo);
                while (scannerFile.hasNextLine()) {
                    if (conversionTo != null && conversionTo.equals("json")) {
                        Map<String, List<Map<String, String>>> map1 = new HashMap<>();
                        List<Map<String, String>> map2 = new ArrayList<>();
                        String input = scannerFile.nextLine().replaceAll("[\\[\\]]", "");
                        String[] strings1 = input.split("=", 2);
                        String[] strings2 = strings1[1].split("\\|");
                        for (String s1 : strings2) {
                            String[] strings3 = s1.split(";");
                            Map<String, String> map3 = new HashMap<>();
                            for (String s2 : strings3) {
                                String[] strings4 = s2.split("=");
                                map3.put(strings4[0], strings4[1]);
                            }
                            map2.add(map3);
                        }
                        map1.put(strings1[0], map2);
                        String writeValueAsString = new ObjectMapper().writeValueAsString(map1);
                        fileWriter.write(writeValueAsString + "\n");
                    } else {
                        fileWriter.write(scannerFile.nextLine() + "\n");
                    }
                }

                fileWriter.flush();
                fileWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

}
