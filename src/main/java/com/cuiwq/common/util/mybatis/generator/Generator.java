package com.cuiwq.common.util.mybatis.generator;

import com.cuiwq.common.util.FileUtil;
import com.cuiwq.common.util.mybatis.generator.DatabaseMetaDateApplication.Table;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import java.io.File;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 代码生成器
 *
 * @author cuiwq
 * @date 2019-01-08 星期二
 */
public class Generator {
    
    private static final String PATH_SEPERATOR = "/";
    private static final String PACKAGE_SEPERATOR = ".";
    
    private static final String TEMPLATE_FOLDER = "/generatorTemplates/";
    
    private static final String BASE_MAPPER_FILE = "BaseMapper.xml";
    private static final String QUERY_FILE = "Query.java";
    private static final String ID_DO_FILE = "IdDO.java";
    private static final String BASE_DO_FILE = "BaseDO.java";
    private static final String DAO_FILE = "BaseDAO.java";
    private static final String BASE_SERVICE_FILE = "BaseService.java";
    private static final String BASE_SERVICE_IMPL_FILE = "BaseServiceImpl.java";
    private static final String BASE_MAPPER_TEMPLATE = TEMPLATE_FOLDER + "BaseMapperTemplate.vm";
    private static final String QUERY_TEMPLATE = TEMPLATE_FOLDER + "QueryTemplate.vm";
    private static final String ID_DO_TEMPLATE = TEMPLATE_FOLDER + "IdDOTemplate.vm";
    private static final String BASE_DO_TEMPLATE = TEMPLATE_FOLDER + "BaseDOTemplate.vm";
    private static final String BASE_BAO_TEMPLATE = TEMPLATE_FOLDER + "BaseDAOTemplate.vm";
    private static final String BASE_SERVICE_TEMPLATE = TEMPLATE_FOLDER + "BaseServiceTemplate.vm";
    private static final String BASE_SERVICE_IMPL_TEMPLATE = TEMPLATE_FOLDER + "BaseServiceImplTemplate.vm";
    
    private static final String MAPPER_SUFFIX = "Mapper.xml";
    private static final String DO_SUFFIX = "DO.java";
    private static final String DAO_SUFFIX = "DAO.java";
    private static final String SERVICE_SUFFIX = "Service.java";
    private static final String SERVICE_IMPL_SUFFIX = "ServiceImpl.java";
    private static final String MAPPER_TEMPLATE = TEMPLATE_FOLDER + "MapperTemplate.vm";
    private static final String DO_TEMPLATE = TEMPLATE_FOLDER + "DOTemplate.vm";
    private static final String DAO_TEMPLATE = TEMPLATE_FOLDER + "DAOTemplate.vm";
    private static final String SERVICE_TEMPLATE = TEMPLATE_FOLDER + "ServiceTemplate.vm";
    private static final String SERVICE_IMPL_TEMPLATE = TEMPLATE_FOLDER + "ServiceImplTemplate.vm";
    
    private VelocityEngine velocityEngine;
    
    private VelocityContext velocityContext;
    
    /**
     * 项目路径
     */
    private String projectDirectory;
    
    /**
     * JDBC驱动名
     */
    private String jdbcDriver;
    
    /**
     * 数据库链接
     */
    private String jdbcUrl;
    
    /**
     * 数据库用户名
     */
    private String jdbcUser;
    
    /**
     * 数据库用户密码
     */
    private String jdbcPasswd;
    
    /**
     * 作者
     */
    private String author;
    
    /**
     * 表名前缀，留空表示不使用前缀
     */
    private String tableNamePrefix;
    
    /**
     * 表名
     */
    private String tableName;
    
    /**
     * 项目包名，开头无"."，结尾有"."
     */
    private String basePackage;
    
    /**
     * 项目包路径，开头无"/"，结尾有"/"
     */
    private String baseDirtory;
    
    /**
     * Mapper目录，开头无"/"，结尾有"/"
     */
    private String mapperDirtory;
    
    /**
     * DO包相对路径，开头无"/"，结尾有"/"
     */
    private String doDirtory;
    
    /**
     * DAO包相对路径，开头无"/"，结尾有"/"
     */
    private String daoDirtory;
    
    /**
     * Service包相对路径，开头无"/"，结尾有"/"
     */
    private String serviceDirtory;
    
    /**
     * 使用枚举类的字段
     * key = field_name, value = EnumClassName
     */
    private Map<String, String> enumField;
    
    /**
     * 是否生成mapper
     */
    private boolean makeMapper;
    
    /**
     * 是否生成DAO
     */
    private boolean makeDao;
    
    /**
     * 是否生成DO
     */
    private boolean makeDo;
    
    /**
     * 是否生成Service
     */
    private boolean makeService;
    
    private String upperName;
    
    public static GeneratorBuilder builder(String jdbcUrl, String jdbcUser, String jdbcPasswd, String tableName) {
        return GeneratorBuilder.create(jdbcUrl, jdbcUser, jdbcPasswd, tableName);
    }
    
    public Generator(GeneratorBuilder builder) {
        this.jdbcDriver = builder.jdbcDriver;
        this.jdbcUrl = builder.jdbcUrl;
        this.jdbcUser = builder.jdbcUser;
        this.jdbcPasswd = builder.jdbcPasswd;
        this.author = builder.author;
        this.tableNamePrefix = builder.tableNamePrefix;
        this.tableName = builder.tableName;
        this.basePackage = builder.basePackage;
        this.baseDirtory = builder.baseDirtory;
        this.mapperDirtory = builder.mapperDirtory;
        this.doDirtory = builder.doDirtory;
        this.daoDirtory = builder.daoDirtory;
        this.serviceDirtory = builder.serviceDirtory;
        this.enumField = builder.enumField;
        this.makeMapper = builder.makeMapper;
        this.makeDao = builder.makeDao;
        this.makeDo = builder.makeDo;
        this.makeService = builder.makeService;
        init();
    }
    
    private void init() {
        velocityEngine = getVelocityEngine();
        velocityContext = new VelocityContext();
        upperName = ColumnNameUtil.columnToUpperCamelCase(tableName);
        String lowerName = ColumnNameUtil.columnToLowerCamelCase(tableName);
    
        File file = new File("1");
        projectDirectory = file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - 1);
        
        String fullTableName = tableNamePrefix + tableName;
        String datetime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd E"));
        Table table = new DatabaseMetaDateApplication(jdbcDriver, jdbcUrl, jdbcUser, jdbcPasswd).getTableColumns(null, fullTableName);
        if(table.columns.isEmpty()) {
            throw new IllegalArgumentException("未找到数据表");
        }
        velocityContext.put("author", author);
        velocityContext.put("tableName", fullTableName);
        velocityContext.put("tableRemark", table.remark);
        velocityContext.put("datetime", datetime);
        velocityContext.put("upperName", upperName);
        velocityContext.put("basePackage", basePackage);
        velocityContext.put("doPackage", doDirtory.replaceAll(PATH_SEPERATOR, PACKAGE_SEPERATOR).substring(0, doDirtory.length() - 1));
        String daoPackage = daoDirtory.replaceAll(PATH_SEPERATOR, PACKAGE_SEPERATOR).substring(0, daoDirtory.length() - 1);
        velocityContext.put("daoPackage", daoPackage);
        velocityContext.put("servicePackage", serviceDirtory.replaceAll(PATH_SEPERATOR, PACKAGE_SEPERATOR).substring(0, serviceDirtory.length() - 1));
        velocityContext.put("namespace", basePackage + daoPackage + PACKAGE_SEPERATOR + upperName + "DAO");
        velocityContext.put("lowerName", lowerName);
        Iterator<Column> iterator = table.columns.iterator();
        while(iterator.hasNext()) {
            Column column = iterator.next();
            switch(column.getName()) {
                case "id":
                    velocityContext.put("idType", column.getJavaType());
                    velocityContext.put("idJdbcType", column.getJdbcType());
                    iterator.remove();
                    break;
                case "is_deleted":
                case "create_time":
                case "modify_time":
                    iterator.remove();
                    break;
                default:
            }
            String enumType = enumField.get(column.getName());
            if(enumType != null) {
                column.setHaveEnum(true);
                column.setJavaType(enumType);
            }
        }
        velocityContext.put("columnList", table.columns);
    }
    
    private VelocityEngine getVelocityEngine() {
        VelocityEngine velocityEngine = new VelocityEngine();
        try {
            velocityEngine.setProperty("input.encoding", "UTF-8");
            velocityEngine.setProperty("output.encoding", "UTF-8");
            velocityEngine.setProperty(Velocity.RESOURCE_LOADER, "class");
            velocityEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            velocityEngine.init();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return velocityEngine;
    }
    
    public void makeBase() {
        parse(projectDirectory + mapperDirtory + BASE_MAPPER_FILE, BASE_MAPPER_TEMPLATE);
        parse(projectDirectory + baseDirtory + doDirtory + QUERY_FILE, QUERY_TEMPLATE);
        parse(projectDirectory + baseDirtory + doDirtory + ID_DO_FILE, ID_DO_TEMPLATE);
        parse(projectDirectory + baseDirtory + doDirtory + BASE_DO_FILE, BASE_DO_TEMPLATE);
        parse(projectDirectory + baseDirtory + daoDirtory + DAO_FILE, BASE_BAO_TEMPLATE);
        parse(projectDirectory + baseDirtory + serviceDirtory + BASE_SERVICE_FILE, BASE_SERVICE_TEMPLATE);
        parse(projectDirectory + baseDirtory + serviceDirtory + "impl/" + BASE_SERVICE_IMPL_FILE, BASE_SERVICE_IMPL_TEMPLATE);
    }
    
    public void make() {
        if(makeMapper) {
            parse(projectDirectory + mapperDirtory + upperName + MAPPER_SUFFIX, MAPPER_TEMPLATE );
        }
        if(makeDao) {
            parse(projectDirectory + baseDirtory + daoDirtory + upperName + DAO_SUFFIX, DAO_TEMPLATE );
        }
        if(makeDo) {
            parse(projectDirectory + baseDirtory + doDirtory + upperName + DO_SUFFIX, DO_TEMPLATE );
        }
        if(makeService) {
            parse(projectDirectory + baseDirtory + serviceDirtory + upperName + SERVICE_SUFFIX, SERVICE_TEMPLATE );
            parse(projectDirectory + baseDirtory + serviceDirtory + "impl/" + upperName + SERVICE_IMPL_SUFFIX, SERVICE_IMPL_TEMPLATE );
        }
    }
    
    private void parse(String filePath, String templatePath) {
        Template template = velocityEngine.getTemplate(templatePath);
        StringWriter writer = new StringWriter();
        template.merge(velocityContext, writer);
        FileUtil.write(filePath, writer.toString());
    }
    
    public static class GeneratorBuilder {
        
        private String jdbcDriver = "com.mysql.jdbc.Driver";
        private String jdbcUrl;
        private String jdbcUser;
        private String jdbcPasswd;
        private String author = "Generator";
        private String tableNamePrefix = "";
        private String tableName;
        private String basePackage = "";
        private String baseDirtory = "src/main/java/";
        private String mapperDirtory = "src/main/resources/mapper/";
        private String doDirtory = "domain/dbo/";
        private String daoDirtory = "dao/";
        private String serviceDirtory = "service/";
        private Map<String, String> enumField = new HashMap<>();
        private boolean makeMapper = true;
        private boolean makeDao = true;
        private boolean makeDo = true;
        private boolean makeService = true;
    
        private GeneratorBuilder(String jdbcUrl, String jdbcUser, String jdbcPasswd, String tableName) {
            this.jdbcUrl = jdbcUrl;
            this.jdbcUser = jdbcUser;
            this.jdbcPasswd = jdbcPasswd;
            this.tableName = tableName;
        }
    
        /**
         * 使用必要参数，创建一个Builder
         *
         * @param jdbcUrl 数据库连接，必须可以读取数据库源信息
         * @param jdbcUser 数据库用户
         * @param jdbcPasswd 数据库密码
         * @param tableName 需要创建相关类的表名
         * @return 创建得到的Builder
         */
        public static GeneratorBuilder create(String jdbcUrl, String jdbcUser, String jdbcPasswd, String tableName) {
            return new GeneratorBuilder(jdbcUrl, jdbcUser, jdbcPasswd, tableName);
        }
    
        /**
         * 选填参数
         *
         * @param jdbcDriver {@link Generator#jdbcDriver}
         * @return 当前Builder
         */
        public GeneratorBuilder jdbcDriver(String jdbcDriver) {
            this.jdbcDriver = jdbcDriver;
            return this;
        }
    
        /**
         * 选填参数
         *
         * @param author {@link Generator#author}
         * @return 当前Builder
         */
        public GeneratorBuilder author(String author) {
            this.author = author;
            return this;
        }
    
        /**
         * 选填参数
         *
         * @param tableNamePrefix {@link Generator#tableNamePrefix}
         * @return 当前Builder
         */
        public GeneratorBuilder tableNamePrefix(String tableNamePrefix) {
            this.tableNamePrefix = tableNamePrefix;
            return this;
        }
    
        /**
         * 选填参数
         *
         * @param basePackage {@link Generator#basePackage}
         * @return 当前Builder
         */
        public GeneratorBuilder basePackage(String basePackage) {
            this.basePackage = basePackage;
            return this;
        }
    
        /**
         * 选填参数
         *
         * @param baseDirtory {@link Generator#baseDirtory}
         * @return 当前Builder
         */
        public GeneratorBuilder baseDirtory(String baseDirtory) {
            this.baseDirtory = baseDirtory;
            return this;
        }
    
        /**
         * 选填参数
         *
         * @param mapperDirtory {@link Generator#mapperDirtory}
         * @return 当前Builder
         */
        public GeneratorBuilder mapperDirtory(String mapperDirtory) {
            this.mapperDirtory = mapperDirtory;
            return this;
        }
    
        /**
         * 选填参数
         *
         * @param doDirtory {@link Generator#doDirtory}
         * @return 当前Builder
         */
        public GeneratorBuilder doDirtory(String doDirtory) {
            this.doDirtory = doDirtory;
            return this;
        }
    
        /**
         * 选填参数
         *
         * @param daoDirtory {@link Generator#daoDirtory}
         * @return 当前Builder
         */
        public GeneratorBuilder daoDirtory(String daoDirtory) {
            this.daoDirtory = daoDirtory;
            return this;
        }
    
        /**
         * 选填参数
         *
         * @param serviceDirtory {@link Generator#serviceDirtory}
         * @return 当前Builder
         */
        public GeneratorBuilder serviceDirtory(String serviceDirtory) {
            this.serviceDirtory = serviceDirtory;
            return this;
        }
    
        /**
         * 增加一个枚举类字段
         * @param fieldName 数据库字段名
         * @param className 枚举类名
         * @return 当前Builder
         */
        public GeneratorBuilder addEnum(String fieldName, String className) {
            this.enumField.put(fieldName, className);
            return this;
        }
    
        /**
         * 选填参数
         *
         * @param makeMapper {@link Generator#makeMapper}
         * @return 当前Builder
         */
        public GeneratorBuilder makeMapper(boolean makeMapper) {
            this.makeMapper = makeMapper;
            return this;
        }
    
        /**
         * 选填参数
         *
         * @param makeDao {@link Generator#makeDao}
         * @return 当前Builder
         */
        public GeneratorBuilder makeDao(boolean makeDao) {
            this.makeDao = makeDao;
            return this;
        }
    
        /**
         * 选填参数
         *
         * @param makeDo {@link Generator#makeDo}
         * @return 当前Builder
         */
        public GeneratorBuilder makeDo(boolean makeDo) {
            this.makeDo = makeDo;
            return this;
        }
    
        /**
         * 选填参数
         *
         * @param makeService {@link Generator#makeService}
         * @return 当前Builder
         */
        public GeneratorBuilder makeService(boolean makeService) {
            this.makeService = makeService;
            return this;
        }
    
        /**
         * 使用该Builder构造Generator
         * @return 构造好的Generator
         */
        public Generator build() {
            return new Generator(this);
        }
    }
    
}
