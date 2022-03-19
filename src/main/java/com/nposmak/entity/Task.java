package com.nposmak.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "user_task_table")
public class Task {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task")
    private String taskDescription;
}
