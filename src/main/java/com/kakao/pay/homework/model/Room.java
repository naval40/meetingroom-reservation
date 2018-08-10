package com.kakao.pay.homework.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor(staticName="of")
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long roomId;

    @NonNull
    private String name;

}
