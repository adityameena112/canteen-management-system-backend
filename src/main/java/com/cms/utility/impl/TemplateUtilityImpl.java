//package com.cms.utility.impl;
//
//import java.io.StringWriter;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.Set;
//
//import com.cms.utility.TemplateUtility;
//import org.apache.velocity.Template;
//import org.apache.velocity.VelocityContext;
//import org.apache.velocity.app.VelocityEngine;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//
//@Component
//public class TemplateUtilityImpl implements TemplateUtility {
//
//    @Autowired
//    private VelocityEngine velocityEngine;
//
//    @Override
//    public String getText(final String templateName, Map<String, Object> params) {
//        return this.getTemplate(templateName, params);
//    }
//
//    private String getTemplate(final String path, final Map<String, Object> params) {
//        Template template = velocityEngine.getTemplate(path);
//
//        VelocityContext context = new VelocityContext();
//
//        Set<Entry<String, Object>> entries = params.entrySet();
//
//        for (Entry<String, Object> entry : entries) {
//            context.put(entry.getKey(), entry.getValue());
//        }
//
//        StringWriter writer = new StringWriter();
//
//        template.merge(context, writer);
//
//        return writer.toString();
//    }
//
//}
