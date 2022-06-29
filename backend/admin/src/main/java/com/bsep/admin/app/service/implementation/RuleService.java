package com.bsep.admin.app.service.implementation;

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

import com.bsep.admin.app.dto.RuleLogDto;

@Service
public class RuleService {

    @Value("${logRulePath}")
    private String logRulePath;
    @Value("${logRuleDrtPath}")
    private String logRuleDrtPath;
    @Value("${kjarPath}")
    private String kjarPath;

    public RuleService() {
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
