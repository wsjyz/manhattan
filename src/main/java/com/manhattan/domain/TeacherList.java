package com.manhattan.domain;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.StringType;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lk.zh on 2014/6/17.
 */
public class TeacherList implements UserType {

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.JAVA_OBJECT};
    }

    @Override
    public Class returnedClass() {
        return List.class;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        if (x==y) {
            return true;
        }
        if(x!=null&&y!=null){
            List xList=(List)x;
            List ylList=(List)y;
            if (xList.size()!=ylList.size()) {
                return false;
            }
            for(int i=0;i<xList.size();i++){
                String s1=(String)xList.get(i);
                String s2=(String)ylList.get(i);
                if (!s1.equals(s2)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        String str= (String) StringType.INSTANCE.get(rs, names[0], session);
        List teacherList=null;
        if(str!=null){
            teacherList=new ArrayList();
            for(String one:str.split(",")){
                teacherList.add(one);
            }
        }
        return teacherList;

    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        if(value!=null){
            List teacherList=(List)value;
            StringBuilder sb=new StringBuilder();
            for(int i=0;i<teacherList.size()-1;i++){
                sb.append(teacherList.get(i)).append(";");
            }
            sb.append(teacherList.get(teacherList.size()-1));
            StringType.INSTANCE.nullSafeSet(st, sb.toString(), index, session);
        }
        else{
            StringType.INSTANCE.nullSafeSet(st, value, index, session);
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        if(value!=null){
            List source =(List)value;
            List target =new ArrayList();
            target.addAll(source);
            return target;
        }
        else{
            return value;
        }
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return null;
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return null;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return null;
    }
}
