package application.driver.implementations.services;

import application.driver.exceptions.ApplicationException;
import application.driver.exceptions.ApplicationExceptionCauseEnum;
import application.driver.implementations.models.Log;
import application.driver.interfaces.IInputService;
import application.driver.interfaces.ILog;

import java.util.Scanner;

public class ConsoleInputServiceImpl implements IInputService {
    @Override
    public ILog takeInput() {

        System.out.println("\tEnter a new log...\n");

        Scanner scanner = new Scanner(System.in);

        StringBuilder log = new StringBuilder();
        String currentLine = scanner.nextLine();
        log.append(currentLine);

        if(!log.isEmpty())
            return new Log(log.toString());

        else throw new ApplicationException(ApplicationExceptionCauseEnum.NULL_INPUT);
    }

//        try (InputStreamReader in = new InputStreamReader(System.in);
//             BufferedReader buffer = new BufferedReader(in)) {
//            final String fileAsText = buffer.lines().collect(Collectors.joining("\n"));
//            System.out.println(fileAsText);
//
//            if (fileAsText.length() != 0)
//                return new Log(fileAsText);
//
//            else throw new ApplicationException(ExceptionReasonEnum.NULL_INPUT);
//        } catch (Exception e) {
//            throw new ApplicationException(ExceptionReasonEnum.NULL_INPUT);
//        }
//    }
}
