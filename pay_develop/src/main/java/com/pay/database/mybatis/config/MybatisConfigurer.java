//package com.pay.database.mybatis.config;
//
//import org.springframework.boot.autoconfigure.AutoConfigureAfter;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.test.context.jdbc.Sql;
//import tk.mybatis.spring.mapper.MapperScannerConfigurer;
//
//import javax.annotation.Resource;
//import javax.sql.DataSource;
//
//@Configuration
//public class MybatisConfigurer {
//    @Resource
//    private DataSource dataSource;
//
//    @Configuration
//    @AutoConfigureAfter(MybatisConfigurer.class)
//    public static class MybatisMapperScannerConfigurer{
//        public MapperScannerConfigurer mapperScannerConfigurer(){
//            MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
//            mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
//            return mapperScannerConfigurer;
//        }
//    }
//}
