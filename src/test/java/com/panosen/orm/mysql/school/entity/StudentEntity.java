package com.panosen.orm.mysql.school.entity;

import com.panosen.orm.annonation.*;
import com.panosen.orm.mysql.DBNameConst;

import java.sql.Timestamp;
import java.sql.Types;

@Entity
@DataSource(name = DBNameConst.school)
@Table(name = "student")
public class StudentEntity {

    @Id
    @Column(name = "id")
    @Type(type = Types.INTEGER)
    private Integer id;

    @Column(name = "name")
    @Type(type = Types.VARCHAR)
    private String name;

    @Column(name = "data_status")
    @Type(type = Types.INTEGER)
    private Integer dataStatus;

    @Column(name = "create_time")
    @Type(type = Types.TIMESTAMP)
    private Timestamp createTime;

    @Column(name = "last_update_time")
    @Type(type = Types.TIMESTAMP)
    private Timestamp lastUpdateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(Integer dataStatus) {
        this.dataStatus = dataStatus;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Timestamp lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
