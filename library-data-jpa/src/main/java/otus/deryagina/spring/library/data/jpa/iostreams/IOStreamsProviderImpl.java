package otus.deryagina.spring.library.data.jpa.iostreams;


import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Service
public class IOStreamsProviderImpl implements IOStreamsProvider {

    private final InputStream inputStream;
    private final PrintStream printStream;
    private final Scanner scanner;

    public IOStreamsProviderImpl() {
        this.inputStream = System.in;
        this.printStream = System.out;
        scanner = new Scanner(inputStream);
    }

    @Override
    public void printInfo(String info) {
        printStream.println(info);
    }

    @Override
    public String readData() {
        return scanner.nextLine();
    }
}
