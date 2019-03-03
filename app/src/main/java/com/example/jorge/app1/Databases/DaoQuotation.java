package com.example.jorge.app1.Databases;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Query;
import android.support.annotation.*;
import com.example.jorge.app1.Pojo.Quotation;

import java.util.List;

@Dao
public interface DaoQuotation {
@Insert
public void addQuotation(Quotation q);
@Delete
public void deleteQuotation(Quotation q);

@Query("SELECT * FROM QuotationsTable")
public List<Quotation> getAllQuotation();
@Query("SELECT * FROM QuotationsTable WHERE quote =:text")
public Quotation searchQuotation(String text);
@Query("DELETE  FROM QuotationsTable")
public void deleteAllQuotation();

}
