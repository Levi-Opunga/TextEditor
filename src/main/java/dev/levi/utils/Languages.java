package dev.levi.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Languages {

    /**
     * Style meaning don't syntax highlight anything.
     */
            String SYNTAX_STYLE_NONE			= "text/plain";


    /**
     * Style for highlighting ActionScript.
     */
    String SYNTAX_STYLE_ACTIONSCRIPT	= "text/actionscript";


    /**
     * Style for highlighting x86 assembler.
     */
    String SYNTAX_STYLE_ASSEMBLER_X86	= "text/asm";


    /**
     * Style for highlighting x86 assembler.
     */
    String SYNTAX_STYLE_ASSEMBLER_6502	= "text/asm6502";


    /**
     * Style for highlighting BBCode.
     */
    String SYNTAX_STYLE_BBCODE			= "text/bbcode";


    /**
     * Style for highlighting C.
     */
    String SYNTAX_STYLE_C				= "text/c";


    /**
     * Style for highlighting Clojure.
     */
    String SYNTAX_STYLE_CLOJURE			= "text/clojure";


    /**
     * Style for highlighting C++.
     */
    String SYNTAX_STYLE_CPLUSPLUS		= "text/cpp";


    /**
     * Style for highlighting C#.
     */
    String SYNTAX_STYLE_CSHARP			= "text/cs";


    /**
     * Style for highlighting CSS.
     */
    String SYNTAX_STYLE_CSS			= "text/css";


    /**
     * Style for highlighting CSV.
     */
    String SYNTAX_STYLE_CSV			= "text/csv";


    /**
     * Syntax style for highlighting D.
     */
    String SYNTAX_STYLE_D			= "text/d";


    /**
     * Syntax style for highlighting Dockerfiles.
     */
    String SYNTAX_STYLE_DOCKERFILE		= "text/dockerfile";


    /**
     * Style for highlighting Dart.
     */
    String SYNTAX_STYLE_DART		= "text/dart";


    /**
     * Style for highlighting Delphi/Pascal.
     */
    String SYNTAX_STYLE_DELPHI			= "text/delphi";


    /**
     * Style for highlighting DTD files.
     */
    String SYNTAX_STYLE_DTD			= "text/dtd";


    /**
     * Style for highlighting Fortran.
     */
    String SYNTAX_STYLE_FORTRAN			= "text/fortran";


    /**
     * Style for highlighting go.
     */
    String SYNTAX_STYLE_GO				= "text/golang";


    /**
     * Style for highlighting Groovy.
     */
    String SYNTAX_STYLE_GROOVY			= "text/groovy";


    /**
     * Style for highlighting Handlebars files.
     */
    String SYNTAX_STYLE_HANDLEBARS		= "text/handlebars";


    /**
     * Style for highlighting hosts files.
     */
    String SYNTAX_STYLE_HOSTS			= "text/hosts";


    /**
     * Style for highlighting .htaccess files.
     */
    String SYNTAX_STYLE_HTACCESS		= "text/htaccess";


    /**
     * Style for highlighting HTML.
     */
    String SYNTAX_STYLE_HTML			= "text/html";


    /**
     * Style for highlighting INI files.
     */
    String SYNTAX_STYLE_INI			= "text/ini";


    /**
     * Style for highlighting Java.
     */
    String SYNTAX_STYLE_JAVA			= "text/java";


    /**
     * Style for highlighting JavaScript.
     */
    String SYNTAX_STYLE_JAVASCRIPT		= "text/javascript";


    /**
     * Style for highlighting JSON.
     */
    String SYNTAX_STYLE_JSON		= "text/json";


    /**
     * Style for highlighting .jshintrc files (JSON with comments, so can be
     * used for other times when you want this behavior).
     */
    String SYNTAX_STYLE_JSON_WITH_COMMENTS	= "text/jshintrc";


    /**
     * Style for highlighting JSP.
     */
    String SYNTAX_STYLE_JSP			= "text/jsp";


    /**
     * Style for highlighting Kotlin.
     */
    String SYNTAX_STYLE_KOTLIN		= "text/kotlin";


    /**
     * Style for highlighting LaTeX.
     */
    String SYNTAX_STYLE_LATEX		= "text/latex";


    /**
     * Style for highlighting Less.
     */
    String SYNTAX_STYLE_LESS		= "text/less";


    /**
     * Style for highlighting Lisp.
     */
    String SYNTAX_STYLE_LISP		= "text/lisp";


    /**
     * Style for highlighting Lua.
     */
    String SYNTAX_STYLE_LUA			= "text/lua";


    /**
     * Style for highlighting makefiles.
     */
    String SYNTAX_STYLE_MAKEFILE		= "text/makefile";


    /**
     * Style for highlighting markdown.
     */
    String SYNTAX_STYLE_MARKDOWN		= "text/markdown";


    /**
     * Style for highlighting MXML.
     */
    String SYNTAX_STYLE_MXML			= "text/mxml";


    /**
     * Style for highlighting NSIS install scripts.
     */
    String SYNTAX_STYLE_NSIS			= "text/nsis";


    /**
     * Style for highlighting Perl.
     */
    String SYNTAX_STYLE_PERL			= "text/perl";


    /**
     * Style for highlighting PHP.
     */
    String SYNTAX_STYLE_PHP				= "text/php";


    /**
     * Style for highlighbting proto files.
     */
    String SYNTAX_STYLE_PROTO			= "text/proto";


    /**
     * Style for highlighting properties files.
     */
    String SYNTAX_STYLE_PROPERTIES_FILE	= "text/properties";


    /**
     * Style for highlighting Python.
     */
    String SYNTAX_STYLE_PYTHON			= "text/python";


    /**
     * Style for highlighting Ruby.
     */
    String SYNTAX_STYLE_RUBY			= "text/ruby";


    /**
     * Style for highlighting SAS keywords.
     */
    String SYNTAX_STYLE_SAS			= "text/sas";


    /**
     * Style for highlighting Scala.
     */
    String SYNTAX_STYLE_SCALA		= "text/scala";


    /**
     * Style for highlighting SQL.
     */
    String SYNTAX_STYLE_SQL			= "text/sql";


    /**
     * Style for highlighting Tcl.
     */
    String SYNTAX_STYLE_TCL			= "text/tcl";


    /**
     * Style for highlighting TypeScript.
     */
    String SYNTAX_STYLE_TYPESCRIPT	= "text/typescript";


    /**
     * Style for highlighting UNIX shell keywords.
     */
    String SYNTAX_STYLE_UNIX_SHELL		= "text/unix";


    /**
     * Style for highlighting Visual Basic.
     */
    String SYNTAX_STYLE_VISUAL_BASIC	= "text/vb";


    /**
     * Style for highlighting Windows batch files.
     */
    String SYNTAX_STYLE_WINDOWS_BATCH	= "text/bat";


    /**
     * Style for highlighting XML.
     */
    String SYNTAX_STYLE_XML			= "text/xml";


    /**
     * Syntax style for highlighting YAML.
     */
    String SYNTAX_STYLE_YAML		= "text/yaml";

    public List<String> getAllLanguageNames() {
       return Arrays.stream(this.getClass().getDeclaredFields()).map(feild ->
            feild.toString().replaceAll("java.lang.String dev.levi.utils.Languages.","")
        ).collect(Collectors.toList());


    }

 public static List<String> list = Arrays.asList("plain", "actionscript", "asm", "asm6502", "bbcode", "c", "clojure", "cpp", "cs", "css", "csv", "d", "dockerfile", "dart", "delphi", "dtd", "fortran", "golang", "groovy", "handlebars", "hosts", "htaccess", "html", "ini", "java", "javascript", "json", "jshintrc", "jsp", "kotlin", "latex", "less", "lisp", "lua", "makefile", "markdown", "mxml", "nsis", "perl", "php", "proto", "properties", "python", "ruby", "sas", "scala", "sql", "tcl", "typescript", "unix", "vb", "bat", "xml", "yaml");

  public static   String[] stringArray = list.toArray(new String[list.size()]);

  private void someReflection(){
      Languages languages = new Languages();
      languages.getAllLanguageNames();
      ArrayList<String> list = new ArrayList<String>();

      for (String language : languages.getAllLanguageNames()) {

          // Get the field we want to inspect
          Field field = null;
          try {
              field = Languages.class.getDeclaredField(language);
          } catch (NoSuchFieldException e) {
              throw new RuntimeException(e);
          }

          // Get the getter method for the field
          String getterName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
          Method getter = null;
          try {
              getter = Languages.class.getMethod(getterName);
          } catch (NoSuchMethodException e) {
              throw new RuntimeException(e);
          }

          // Invoke the getter method on the instance and print the result
          String value = null;
          try {
              value = (String) getter.invoke(languages);
          } catch (IllegalAccessException e) {
              throw new RuntimeException(e);
          } catch (InvocationTargetException e) {
              throw new RuntimeException(e);
          }

//list.add(value);
          System.out.println("\""+value+"\"");

      }
      System.out.println(list.toString());
  }

    public String getSYNTAX_STYLE_NONE() {
        return SYNTAX_STYLE_NONE;
    }

    public void setSYNTAX_STYLE_NONE(String SYNTAX_STYLE_NONE) {
        this.SYNTAX_STYLE_NONE = SYNTAX_STYLE_NONE;
    }

    public String getSYNTAX_STYLE_ACTIONSCRIPT() {
        return SYNTAX_STYLE_ACTIONSCRIPT;
    }

    public void setSYNTAX_STYLE_ACTIONSCRIPT(String SYNTAX_STYLE_ACTIONSCRIPT) {
        this.SYNTAX_STYLE_ACTIONSCRIPT = SYNTAX_STYLE_ACTIONSCRIPT;
    }

    public String getSYNTAX_STYLE_ASSEMBLER_X86() {
        return SYNTAX_STYLE_ASSEMBLER_X86;
    }

    public void setSYNTAX_STYLE_ASSEMBLER_X86(String SYNTAX_STYLE_ASSEMBLER_X86) {
        this.SYNTAX_STYLE_ASSEMBLER_X86 = SYNTAX_STYLE_ASSEMBLER_X86;
    }

    public String getSYNTAX_STYLE_ASSEMBLER_6502() {
        return SYNTAX_STYLE_ASSEMBLER_6502;
    }

    public void setSYNTAX_STYLE_ASSEMBLER_6502(String SYNTAX_STYLE_ASSEMBLER_6502) {
        this.SYNTAX_STYLE_ASSEMBLER_6502 = SYNTAX_STYLE_ASSEMBLER_6502;
    }

    public String getSYNTAX_STYLE_BBCODE() {
        return SYNTAX_STYLE_BBCODE;
    }

    public void setSYNTAX_STYLE_BBCODE(String SYNTAX_STYLE_BBCODE) {
        this.SYNTAX_STYLE_BBCODE = SYNTAX_STYLE_BBCODE;
    }

    public String getSYNTAX_STYLE_C() {
        return SYNTAX_STYLE_C;
    }

    public void setSYNTAX_STYLE_C(String SYNTAX_STYLE_C) {
        this.SYNTAX_STYLE_C = SYNTAX_STYLE_C;
    }

    public String getSYNTAX_STYLE_CLOJURE() {
        return SYNTAX_STYLE_CLOJURE;
    }

    public void setSYNTAX_STYLE_CLOJURE(String SYNTAX_STYLE_CLOJURE) {
        this.SYNTAX_STYLE_CLOJURE = SYNTAX_STYLE_CLOJURE;
    }

    public String getSYNTAX_STYLE_CPLUSPLUS() {
        return SYNTAX_STYLE_CPLUSPLUS;
    }

    public void setSYNTAX_STYLE_CPLUSPLUS(String SYNTAX_STYLE_CPLUSPLUS) {
        this.SYNTAX_STYLE_CPLUSPLUS = SYNTAX_STYLE_CPLUSPLUS;
    }

    public String getSYNTAX_STYLE_CSHARP() {
        return SYNTAX_STYLE_CSHARP;
    }

    public void setSYNTAX_STYLE_CSHARP(String SYNTAX_STYLE_CSHARP) {
        this.SYNTAX_STYLE_CSHARP = SYNTAX_STYLE_CSHARP;
    }

    public String getSYNTAX_STYLE_CSS() {
        return SYNTAX_STYLE_CSS;
    }

    public void setSYNTAX_STYLE_CSS(String SYNTAX_STYLE_CSS) {
        this.SYNTAX_STYLE_CSS = SYNTAX_STYLE_CSS;
    }

    public String getSYNTAX_STYLE_CSV() {
        return SYNTAX_STYLE_CSV;
    }

    public void setSYNTAX_STYLE_CSV(String SYNTAX_STYLE_CSV) {
        this.SYNTAX_STYLE_CSV = SYNTAX_STYLE_CSV;
    }

    public String getSYNTAX_STYLE_D() {
        return SYNTAX_STYLE_D;
    }

    public void setSYNTAX_STYLE_D(String SYNTAX_STYLE_D) {
        this.SYNTAX_STYLE_D = SYNTAX_STYLE_D;
    }

    public String getSYNTAX_STYLE_DOCKERFILE() {
        return SYNTAX_STYLE_DOCKERFILE;
    }

    public void setSYNTAX_STYLE_DOCKERFILE(String SYNTAX_STYLE_DOCKERFILE) {
        this.SYNTAX_STYLE_DOCKERFILE = SYNTAX_STYLE_DOCKERFILE;
    }

    public String getSYNTAX_STYLE_DART() {
        return SYNTAX_STYLE_DART;
    }

    public void setSYNTAX_STYLE_DART(String SYNTAX_STYLE_DART) {
        this.SYNTAX_STYLE_DART = SYNTAX_STYLE_DART;
    }

    public String getSYNTAX_STYLE_DELPHI() {
        return SYNTAX_STYLE_DELPHI;
    }

    public void setSYNTAX_STYLE_DELPHI(String SYNTAX_STYLE_DELPHI) {
        this.SYNTAX_STYLE_DELPHI = SYNTAX_STYLE_DELPHI;
    }

    public String getSYNTAX_STYLE_DTD() {
        return SYNTAX_STYLE_DTD;
    }

    public void setSYNTAX_STYLE_DTD(String SYNTAX_STYLE_DTD) {
        this.SYNTAX_STYLE_DTD = SYNTAX_STYLE_DTD;
    }

    public String getSYNTAX_STYLE_FORTRAN() {
        return SYNTAX_STYLE_FORTRAN;
    }

    public void setSYNTAX_STYLE_FORTRAN(String SYNTAX_STYLE_FORTRAN) {
        this.SYNTAX_STYLE_FORTRAN = SYNTAX_STYLE_FORTRAN;
    }

    public String getSYNTAX_STYLE_GO() {
        return SYNTAX_STYLE_GO;
    }

    public void setSYNTAX_STYLE_GO(String SYNTAX_STYLE_GO) {
        this.SYNTAX_STYLE_GO = SYNTAX_STYLE_GO;
    }

    public String getSYNTAX_STYLE_GROOVY() {
        return SYNTAX_STYLE_GROOVY;
    }

    public void setSYNTAX_STYLE_GROOVY(String SYNTAX_STYLE_GROOVY) {
        this.SYNTAX_STYLE_GROOVY = SYNTAX_STYLE_GROOVY;
    }

    public String getSYNTAX_STYLE_HANDLEBARS() {
        return SYNTAX_STYLE_HANDLEBARS;
    }

    public void setSYNTAX_STYLE_HANDLEBARS(String SYNTAX_STYLE_HANDLEBARS) {
        this.SYNTAX_STYLE_HANDLEBARS = SYNTAX_STYLE_HANDLEBARS;
    }

    public String getSYNTAX_STYLE_HOSTS() {
        return SYNTAX_STYLE_HOSTS;
    }

    public void setSYNTAX_STYLE_HOSTS(String SYNTAX_STYLE_HOSTS) {
        this.SYNTAX_STYLE_HOSTS = SYNTAX_STYLE_HOSTS;
    }

    public String getSYNTAX_STYLE_HTACCESS() {
        return SYNTAX_STYLE_HTACCESS;
    }

    public void setSYNTAX_STYLE_HTACCESS(String SYNTAX_STYLE_HTACCESS) {
        this.SYNTAX_STYLE_HTACCESS = SYNTAX_STYLE_HTACCESS;
    }

    public String getSYNTAX_STYLE_HTML() {
        return SYNTAX_STYLE_HTML;
    }

    public void setSYNTAX_STYLE_HTML(String SYNTAX_STYLE_HTML) {
        this.SYNTAX_STYLE_HTML = SYNTAX_STYLE_HTML;
    }

    public String getSYNTAX_STYLE_INI() {
        return SYNTAX_STYLE_INI;
    }

    public void setSYNTAX_STYLE_INI(String SYNTAX_STYLE_INI) {
        this.SYNTAX_STYLE_INI = SYNTAX_STYLE_INI;
    }

    public String getSYNTAX_STYLE_JAVA() {
        return SYNTAX_STYLE_JAVA;
    }

    public void setSYNTAX_STYLE_JAVA(String SYNTAX_STYLE_JAVA) {
        this.SYNTAX_STYLE_JAVA = SYNTAX_STYLE_JAVA;
    }

    public String getSYNTAX_STYLE_JAVASCRIPT() {
        return SYNTAX_STYLE_JAVASCRIPT;
    }

    public void setSYNTAX_STYLE_JAVASCRIPT(String SYNTAX_STYLE_JAVASCRIPT) {
        this.SYNTAX_STYLE_JAVASCRIPT = SYNTAX_STYLE_JAVASCRIPT;
    }

    public String getSYNTAX_STYLE_JSON() {
        return SYNTAX_STYLE_JSON;
    }

    public void setSYNTAX_STYLE_JSON(String SYNTAX_STYLE_JSON) {
        this.SYNTAX_STYLE_JSON = SYNTAX_STYLE_JSON;
    }

    public String getSYNTAX_STYLE_JSON_WITH_COMMENTS() {
        return SYNTAX_STYLE_JSON_WITH_COMMENTS;
    }

    public void setSYNTAX_STYLE_JSON_WITH_COMMENTS(String SYNTAX_STYLE_JSON_WITH_COMMENTS) {
        this.SYNTAX_STYLE_JSON_WITH_COMMENTS = SYNTAX_STYLE_JSON_WITH_COMMENTS;
    }

    public String getSYNTAX_STYLE_JSP() {
        return SYNTAX_STYLE_JSP;
    }

    public void setSYNTAX_STYLE_JSP(String SYNTAX_STYLE_JSP) {
        this.SYNTAX_STYLE_JSP = SYNTAX_STYLE_JSP;
    }

    public String getSYNTAX_STYLE_KOTLIN() {
        return SYNTAX_STYLE_KOTLIN;
    }

    public void setSYNTAX_STYLE_KOTLIN(String SYNTAX_STYLE_KOTLIN) {
        this.SYNTAX_STYLE_KOTLIN = SYNTAX_STYLE_KOTLIN;
    }

    public String getSYNTAX_STYLE_LATEX() {
        return SYNTAX_STYLE_LATEX;
    }

    public void setSYNTAX_STYLE_LATEX(String SYNTAX_STYLE_LATEX) {
        this.SYNTAX_STYLE_LATEX = SYNTAX_STYLE_LATEX;
    }

    public String getSYNTAX_STYLE_LESS() {
        return SYNTAX_STYLE_LESS;
    }

    public void setSYNTAX_STYLE_LESS(String SYNTAX_STYLE_LESS) {
        this.SYNTAX_STYLE_LESS = SYNTAX_STYLE_LESS;
    }

    public String getSYNTAX_STYLE_LISP() {
        return SYNTAX_STYLE_LISP;
    }

    public void setSYNTAX_STYLE_LISP(String SYNTAX_STYLE_LISP) {
        this.SYNTAX_STYLE_LISP = SYNTAX_STYLE_LISP;
    }

    public String getSYNTAX_STYLE_LUA() {
        return SYNTAX_STYLE_LUA;
    }

    public void setSYNTAX_STYLE_LUA(String SYNTAX_STYLE_LUA) {
        this.SYNTAX_STYLE_LUA = SYNTAX_STYLE_LUA;
    }

    public String getSYNTAX_STYLE_MAKEFILE() {
        return SYNTAX_STYLE_MAKEFILE;
    }

    public void setSYNTAX_STYLE_MAKEFILE(String SYNTAX_STYLE_MAKEFILE) {
        this.SYNTAX_STYLE_MAKEFILE = SYNTAX_STYLE_MAKEFILE;
    }

    public String getSYNTAX_STYLE_MARKDOWN() {
        return SYNTAX_STYLE_MARKDOWN;
    }

    public void setSYNTAX_STYLE_MARKDOWN(String SYNTAX_STYLE_MARKDOWN) {
        this.SYNTAX_STYLE_MARKDOWN = SYNTAX_STYLE_MARKDOWN;
    }

    public String getSYNTAX_STYLE_MXML() {
        return SYNTAX_STYLE_MXML;
    }

    public void setSYNTAX_STYLE_MXML(String SYNTAX_STYLE_MXML) {
        this.SYNTAX_STYLE_MXML = SYNTAX_STYLE_MXML;
    }

    public String getSYNTAX_STYLE_NSIS() {
        return SYNTAX_STYLE_NSIS;
    }

    public void setSYNTAX_STYLE_NSIS(String SYNTAX_STYLE_NSIS) {
        this.SYNTAX_STYLE_NSIS = SYNTAX_STYLE_NSIS;
    }

    public String getSYNTAX_STYLE_PERL() {
        return SYNTAX_STYLE_PERL;
    }

    public void setSYNTAX_STYLE_PERL(String SYNTAX_STYLE_PERL) {
        this.SYNTAX_STYLE_PERL = SYNTAX_STYLE_PERL;
    }

    public String getSYNTAX_STYLE_PHP() {
        return SYNTAX_STYLE_PHP;
    }

    public void setSYNTAX_STYLE_PHP(String SYNTAX_STYLE_PHP) {
        this.SYNTAX_STYLE_PHP = SYNTAX_STYLE_PHP;
    }

    public String getSYNTAX_STYLE_PROTO() {
        return SYNTAX_STYLE_PROTO;
    }

    public void setSYNTAX_STYLE_PROTO(String SYNTAX_STYLE_PROTO) {
        this.SYNTAX_STYLE_PROTO = SYNTAX_STYLE_PROTO;
    }

    public String getSYNTAX_STYLE_PROPERTIES_FILE() {
        return SYNTAX_STYLE_PROPERTIES_FILE;
    }

    public void setSYNTAX_STYLE_PROPERTIES_FILE(String SYNTAX_STYLE_PROPERTIES_FILE) {
        this.SYNTAX_STYLE_PROPERTIES_FILE = SYNTAX_STYLE_PROPERTIES_FILE;
    }

    public String getSYNTAX_STYLE_PYTHON() {
        return SYNTAX_STYLE_PYTHON;
    }

    public void setSYNTAX_STYLE_PYTHON(String SYNTAX_STYLE_PYTHON) {
        this.SYNTAX_STYLE_PYTHON = SYNTAX_STYLE_PYTHON;
    }

    public String getSYNTAX_STYLE_RUBY() {
        return SYNTAX_STYLE_RUBY;
    }

    public void setSYNTAX_STYLE_RUBY(String SYNTAX_STYLE_RUBY) {
        this.SYNTAX_STYLE_RUBY = SYNTAX_STYLE_RUBY;
    }

    public String getSYNTAX_STYLE_SAS() {
        return SYNTAX_STYLE_SAS;
    }

    public void setSYNTAX_STYLE_SAS(String SYNTAX_STYLE_SAS) {
        this.SYNTAX_STYLE_SAS = SYNTAX_STYLE_SAS;
    }

    public String getSYNTAX_STYLE_SCALA() {
        return SYNTAX_STYLE_SCALA;
    }

    public void setSYNTAX_STYLE_SCALA(String SYNTAX_STYLE_SCALA) {
        this.SYNTAX_STYLE_SCALA = SYNTAX_STYLE_SCALA;
    }

    public String getSYNTAX_STYLE_SQL() {
        return SYNTAX_STYLE_SQL;
    }

    public void setSYNTAX_STYLE_SQL(String SYNTAX_STYLE_SQL) {
        this.SYNTAX_STYLE_SQL = SYNTAX_STYLE_SQL;
    }

    public String getSYNTAX_STYLE_TCL() {
        return SYNTAX_STYLE_TCL;
    }

    public void setSYNTAX_STYLE_TCL(String SYNTAX_STYLE_TCL) {
        this.SYNTAX_STYLE_TCL = SYNTAX_STYLE_TCL;
    }

    public String getSYNTAX_STYLE_TYPESCRIPT() {
        return SYNTAX_STYLE_TYPESCRIPT;
    }

    public void setSYNTAX_STYLE_TYPESCRIPT(String SYNTAX_STYLE_TYPESCRIPT) {
        this.SYNTAX_STYLE_TYPESCRIPT = SYNTAX_STYLE_TYPESCRIPT;
    }

    public String getSYNTAX_STYLE_UNIX_SHELL() {
        return SYNTAX_STYLE_UNIX_SHELL;
    }

    public void setSYNTAX_STYLE_UNIX_SHELL(String SYNTAX_STYLE_UNIX_SHELL) {
        this.SYNTAX_STYLE_UNIX_SHELL = SYNTAX_STYLE_UNIX_SHELL;
    }

    public String getSYNTAX_STYLE_VISUAL_BASIC() {
        return SYNTAX_STYLE_VISUAL_BASIC;
    }

    public void setSYNTAX_STYLE_VISUAL_BASIC(String SYNTAX_STYLE_VISUAL_BASIC) {
        this.SYNTAX_STYLE_VISUAL_BASIC = SYNTAX_STYLE_VISUAL_BASIC;
    }

    public String getSYNTAX_STYLE_WINDOWS_BATCH() {
        return SYNTAX_STYLE_WINDOWS_BATCH;
    }

    public void setSYNTAX_STYLE_WINDOWS_BATCH(String SYNTAX_STYLE_WINDOWS_BATCH) {
        this.SYNTAX_STYLE_WINDOWS_BATCH = SYNTAX_STYLE_WINDOWS_BATCH;
    }

    public String getSYNTAX_STYLE_XML() {
        return SYNTAX_STYLE_XML;
    }

    public void setSYNTAX_STYLE_XML(String SYNTAX_STYLE_XML) {
        this.SYNTAX_STYLE_XML = SYNTAX_STYLE_XML;
    }

    public String getSYNTAX_STYLE_YAML() {
        return SYNTAX_STYLE_YAML;
    }

    public void setSYNTAX_STYLE_YAML(String SYNTAX_STYLE_YAML) {
        this.SYNTAX_STYLE_YAML = SYNTAX_STYLE_YAML;
    }
}
