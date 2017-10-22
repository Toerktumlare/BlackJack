package se.andolf.blackjack.util;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.ClassLoader.getSystemResourceAsStream;

/**
 * @author Thomas on 2017-10-22.
 */
public final class NamesUtil {

    private static String fileName = "names.txt";

    public static String getName() {
        final List<String> names = getNames();
        final Random random = new Random();
        return names.get(random.nextInt(names.size()));
    }

    public static List<String> getNames() {
        return read(fileName);
    }

    public static List<String> read(String fileName) {
        try {
            return Files.lines(Paths.get(NamesUtil.class.getClassLoader().getResource(fileName).toURI())).collect(Collectors.toList());
        } catch (IOException | URISyntaxException e) {
            throw new InternalError("Could not read file " + fileName, e);
        }
    }
}
