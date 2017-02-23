package com.xc;

import com.xc.dao.NoteDao;
import com.xc.entity.Note;
import com.xc.util.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NoteApplicationTests {
	@Autowired
	private NoteDao noteDao;

	@Test
	public void dao() {
		Note note = noteDao.selectNoteById("sdfsdfsf");
		System.out.println(JsonUtil.includePropToJson(note));
	}

}
