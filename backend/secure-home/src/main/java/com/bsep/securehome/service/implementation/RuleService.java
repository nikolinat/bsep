package com.bsep.securehome.service.implementation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.codehaus.plexus.util.cli.CommandLineUtils;
import org.drools.template.ObjectDataCompiler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bsep.securehome.dto.RuleDto;
import com.bsep.securehome.dto.RuleLogDto;
import com.bsep.securehome.model.enums.DeviceType;

@Service
public class RuleService {

    @Value("${airConditioningPath}")
    private String airConditioningPath;
    @Value("${heatingPath}")
    private String heatingPath;
    @Value("${newRulePath}")
    private String newRulePath;
    @Value("${logRulePath}")
    private String logRulePath;
    @Value("${projectPath}")
    private String projectPath;
    @Value("${kjarPath}")
    private String kjarPath;
    @Value("${airConditioningDrtPath}")
    private String airConditioningDrtPath;
    @Value("${heatingDrtPath}")
    private String heatingDrtPath;
    @Value("${newRuleDrtPath}")
    private String newRuleDrtPath;
    @Value("${logRuleDrtPath}")
    private String logRuleDrtPath;

    public RuleService() {
    }

    public void template(RuleDto ruleDto) throws IOException {
        ruleDto.setId(UUID.randomUUID());
        String templatePath = newRulePath;
        String drtPath = newRuleDrtPath;
        if (ruleDto.getType().equals(DeviceType.AIR_CONDITIONING)) {
            templatePath = airConditioningPath;
            drtPath = airConditioningDrtPath;
        }else if(ruleDto.getType().equals(DeviceType.HEATING)){
            templatePath = heatingPath;
            drtPath = heatingDrtPath;
        }
        File drtFile = new File(drtPath);
        InputStream template = new FileInputStream(drtFile);
        List<RuleDto> data = new ArrayList<>();
        data.add(ruleDto);

        ObjectDataCompiler converter = new ObjectDataCompiler();
        String drl = converter.compile(data, template);

        if(ruleDto.getType().equals(DeviceType.AIR_CONDITIONING) || ruleDto.getType().equals(DeviceType.HEATING) ){
            String header = drl.substring(drl.indexOf("package"), drl.indexOf("dialect"));
            String body = drl.substring(drl.indexOf("rule"));

            drl = header.concat(body);
        }else{
            String body = drl.substring(drl.indexOf("rule"));
            drl = body;
        }
       

    
        try {
            if(ruleDto.getType().equals(DeviceType.AIR_CONDITIONING) || ruleDto.getType().equals(DeviceType.HEATING)){
                Files.write(Paths.get(templatePath), drl.getBytes(), StandardOpenOption.CREATE);
            }else{
                Files.write(Paths.get(templatePath), drl.getBytes(), StandardOpenOption.APPEND);
            }
           
        } catch (IOException e) {

        }

        mavenInstall();

    }

    public void logTemplate(RuleLogDto ruleDto) throws IOException {
        ruleDto.setId(UUID.randomUUID());
        File drtFile = new File(logRuleDrtPath);
        InputStream template = new FileInputStream(drtFile);
        List<RuleLogDto> data = new ArrayList<>();
        data.add(ruleDto);

        ObjectDataCompiler converter = new ObjectDataCompiler();
        String drl = converter.compile(data, template);

        String body = drl.substring(drl.indexOf("rule"));
        drl = body;
        try {
            Files.write(Paths.get(logRulePath), drl.getBytes(), StandardOpenOption.APPEND);
           
        } catch (IOException e) {

        }

        mavenInstall();

    }

    public void mavenInstall() throws IOException {

        InvocationRequest requestKjar = new DefaultInvocationRequest();
        requestKjar.setPomFile(new File(kjarPath));
        requestKjar.setGoals(Arrays.asList("clean", "install"));
        Properties envars = CommandLineUtils.getSystemEnvVars();
        String mavenHome = envars.getProperty("M2_HOME");

        Invoker invoker = new DefaultInvoker();
        invoker.setMavenHome(new File(mavenHome));
        try {
            invoker.execute(requestKjar);
        } catch (MavenInvocationException e) {
            e.printStackTrace();
        }
    }
}
