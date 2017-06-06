package com.entity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Created by 63289 on 2017/4/18.
 */
@Entity
@Table(name = "measure", schema = "wearable", catalog = "")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Proxy(lazy = false)
public class MeasureEntity {
    private String accountNumber;
    private Timestamp commitTime;
    private String device;
    private Integer step;
    private Integer distance;
    private Integer heart;
    private int id;

    @Basic
    @Column(name = "accountNumber", nullable = false, length = 10)
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Basic
    @Column(name = "commitTime", nullable = false)
    public Timestamp getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(Timestamp commitTime) {
        this.commitTime = commitTime;
    }

    @Basic
    @Column(name = "device", nullable = true, length = 100)
    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    @Basic
    @Column(name = "step", nullable = true)
    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    @Basic
    @Column(name = "distance", nullable = true)
    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    @Basic
    @Column(name = "heart", nullable = true)
    public Integer getHeart() {
        return heart;
    }

    public void setHeart(Integer heart) {
        this.heart = heart;
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MeasureEntity that = (MeasureEntity) o;

        if (id != that.id) return false;
        if (accountNumber != null ? !accountNumber.equals(that.accountNumber) : that.accountNumber != null)
            return false;
        if (commitTime != null ? !commitTime.equals(that.commitTime) : that.commitTime != null) return false;
        if (device != null ? !device.equals(that.device) : that.device != null) return false;
        if (step != null ? !step.equals(that.step) : that.step != null) return false;
        if (distance != null ? !distance.equals(that.distance) : that.distance != null) return false;
        if (heart != null ? !heart.equals(that.heart) : that.heart != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = accountNumber != null ? accountNumber.hashCode() : 0;
        result = 31 * result + (commitTime != null ? commitTime.hashCode() : 0);
        result = 31 * result + (device != null ? device.hashCode() : 0);
        result = 31 * result + (step != null ? step.hashCode() : 0);
        result = 31 * result + (distance != null ? distance.hashCode() : 0);
        result = 31 * result + (heart != null ? heart.hashCode() : 0);
        result = 31 * result + id;
        return result;
    }

    @Override
    public String toString() {
        return "MeasureEntity{" +
                "accountNumber='" + accountNumber + '\'' +
                ", commitTime=" + commitTime +
                ", device='" + device + '\'' +
                ", step=" + step +
                ", distance=" + distance +
                ", heart=" + heart +
                ", id=" + id +
                '}';
    }
}
