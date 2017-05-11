package jp.eunika.dorenisuru.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@GetMapping
	public Integer test() {
		return jdbcTemplate.queryForObject("select 1 + 1", new MapSqlParameterSource(), Integer.class);
	}
}
