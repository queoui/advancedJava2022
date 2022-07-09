package edu.pdx.cs410J.nmuller;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * A unit test for code in the <code>Project1</code> class.  This is different
 * from <code>Project1IT</code> which is an integration test (and can handle the calls
 * to {@link System#exit(int)} and the like.
 */
class Project2Test extends ErrorCheck {

  @Test
  void readmeCanBeReadAsResource() throws IOException {
    try (
            InputStream readme = Project2.class.getResourceAsStream("README.txt")
    ) {
      assertThat(readme, not(nullValue()));
      BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
      String line = reader.readLine();
      assertThat(line, containsString("This is the README file for the phonebill application!"));
    }
  }

//  @Test
//  void validFilePath(){
//    assertEquals(checkValidPathFile("C:/Users/nmull/OneDrive/Desktop/test.txt"), true);
//
//  }

}
