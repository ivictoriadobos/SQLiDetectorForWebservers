package application.driver.implementations.services;

import application.driver.exceptions.ApplicationException;
import application.driver.exceptions.ApplicationExceptionCauseEnum;
import application.driver.implementations.models.Log;
import application.driver.interfaces.IInputService;
import application.driver.interfaces.ILog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TailBashCommandInputServiceImpl implements IInputService {

    private Process tsharkBashProcess;

    private BufferedReader processOutputStream;

    public TailBashCommandInputServiceImpl() {
        ProcessBuilder pb = new ProcessBuilder("./src/main/resources/scripts/tshark_script.sh").redirectErrorStream(true);

        try {

            tsharkBashProcess = pb.start();

            processOutputStream = new BufferedReader(new InputStreamReader(tsharkBashProcess.getInputStream()));
        }
        catch (IOException e) {

            throw new ApplicationException(ApplicationExceptionCauseEnum.EXCEPTION_AT_STARTING_BASH_COMMAND);
        }

    }

    @Override
    public ILog takeInput() {

        String line;

        try {
            line = processOutputStream.readLine();
        } catch (IOException e) {
            throw new ApplicationException(ApplicationExceptionCauseEnum.EXCEPTION_AT_READING_CONTINUOUS_INPUT_FROM_TSHARK);
        }

        return new Log(line);

    }
}
