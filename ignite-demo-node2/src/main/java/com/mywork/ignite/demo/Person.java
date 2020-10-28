package com.mywork.ignite.demo;

import java.io.Serializable;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by romeh on 19/07/2017.
 */
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Person implements Serializable {
	
	@QuerySqlField(index = true)
	private Integer id;

	private String name;

}
