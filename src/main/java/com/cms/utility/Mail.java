package com.cms.utility;

import java.io.File;
import java.util.Set;
import lombok.Data;

public class Mail {

    private String to;
    private String from;
    private String subject;
    private String body;
    private Set<String> ccs;
    private Set<String> bccs;
    private File attach;

    private String username;
    private String password;

    public Mail() {}

    public Mail(String to, String from, String subject, String body, Set<String> ccs, Set<String> bccs, File attach) {
        super();
        this.to = to;
        this.from = from;
        this.subject = subject;
        this.body = body;
        this.ccs = ccs;
        this.bccs = bccs;
        this.attach = attach;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Set<String> getCcs() {
        return ccs;
    }

    public void setCcs(Set<String> ccs) {
        this.ccs = ccs;
    }

    public Set<String> getBccs() {
        return bccs;
    }

    public void setBccs(Set<String> bccs) {
        this.bccs = bccs;
    }

    public File getAttach() {
        return attach;
    }

    public void setAttach(File attach) {
        this.attach = attach;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
