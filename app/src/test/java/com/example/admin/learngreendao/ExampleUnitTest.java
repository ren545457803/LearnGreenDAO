package com.example.admin.learngreendao;

import org.junit.Test;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        System.out.println("生成吧");
        Schema schema = new Schema(2, "db.ren.ya.bin");

        addNote(schema);

        new DaoGenerator().generateAll(schema, "../app/src/main/java");
        assertEquals(4, 2 + 2);
    }

    private static void addNote(Schema schema) {
        Entity note = schema.addEntity("Note");
        note.addIdProperty();
        note.addStringProperty("text").notNull();
        note.addStringProperty("comment");
        note.addDateProperty("date");
        note.addStringProperty("anotherTest");
        note.addStringProperty("anotherTest2222");
//        note.setHasKeepSections(true);
    }
}