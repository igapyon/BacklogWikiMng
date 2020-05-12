package jp.igapyon.backlog.wiki;

import java.io.File;

import org.junit.Test;

public class BacklogLocal2WikiTest {

    @Test
    public void test() throws Exception {
        BacklogConnection bklConn = BacklogUtil.login( //
                MyBacklogSettings.SPACE_KEY //
                , MyBacklogSettings.API_KEY //
                , MyBacklogSettings.PROJECT_ID);

        BacklogLocal2Wiki local2wiki = new BacklogLocal2Wiki(bklConn);
        local2wiki.process(new File("./target/backlog-wiki.out/"));
    }

}
