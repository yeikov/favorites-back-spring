package com.favorites.back;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import com.favorites.back.entities.viewer.Viewer;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.time.LocalDate;

import org.assertj.core.util.Arrays;

@JsonTest
class ViewerJsonTests {

    @Autowired
    private JacksonTester<Viewer> json;

    @Autowired
    private JacksonTester<Viewer[]> jsonList;

    private Viewer[] viewers;

    @BeforeEach
    void setUp() {
        viewers = Arrays.array(
                new Viewer("Jakie", "chenglong@HongKong.exp", "Hong Kong", LocalDate.parse("2000-11-26")),
                new Viewer("Jet", "leelianjie@pekin.exp", "Beijing", LocalDate.parse("2000-11-26"))
                );
    }

    @Test
    void viewerSerializationTest() throws IOException {
        Viewer viewer = viewers[0];
        assertThat(json.write(viewer)).isStrictlyEqualToJson("single.json");

        assertThat(json.write(viewer)).hasJsonPathStringValue("@.eMail");
        assertThat(json.write(viewer)).extractingJsonPathStringValue("@.eMail")
                .isEqualTo("chenglong@HongKong.exp");
        assertThat(json.write(viewer)).hasJsonPathStringValue("@.name");
        assertThat(json.write(viewer)).extractingJsonPathStringValue("@.name")
                .isEqualTo("Jakie");
    }

    @Test
    void viewerDeserializationTest() throws IOException {
        String expected = """
                {
                    "id": null,
                    "name": "Jakie",
                    "eMail": "chenglong@HongKong.exp",
                    "city": "Hong Kong",
                    "birth": "2000-11-26"
                }
                """;
        //assertThat(json.parse(expected)).isEqualTo(new Viewer("Jakie", "chenglong@HongKong.exp", "Hong Kong", LocalDate.parse("2000-11-26")));
        assertThat(json.parseObject(expected).getName()).isEqualTo("Jakie");
        assertThat(json.parseObject(expected).geteMail()).isEqualTo("chenglong@HongKong.exp");
    }

}

