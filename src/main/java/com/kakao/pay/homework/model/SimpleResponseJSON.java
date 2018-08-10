package com.kakao.pay.homework.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName="of")
public class SimpleResponseJSON {
	@NonNull
	private String message;
	@NonNull
	private Integer responseStatus;
}
