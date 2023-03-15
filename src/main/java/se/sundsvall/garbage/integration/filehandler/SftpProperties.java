package se.sundsvall.garbage.integration.filehandler;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties("integration.sftp")
public class SftpProperties {
  private String username;
  private String password;
  private String remoteHost;
  private String filename;
}
