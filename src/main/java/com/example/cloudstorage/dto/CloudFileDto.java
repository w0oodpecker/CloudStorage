package com.example.cloudstorage.dto;

import lombok.Value;
import org.springframework.core.io.FileSystemResource;

import javax.validation.constraints.NotBlank;
import java.util.List;


public enum CloudFileDto {;

    private interface SourceFileName { @NotBlank String getSourceFileName(); }
    private interface TargetFileName { @NotBlank String getTargetFileName(); }
    private interface Bytes { @NotBlank byte[] getBytes(); }
    private interface FileList { @NotBlank List<?> getFileList(); }
    private interface FileResource { @NotBlank FileSystemResource getFileResource(); }


    public enum RequestDeleteFile {;
        @Value public static class Create implements SourceFileName {
            String SourceFileName;
        }
    }

    public enum  RequestEditFile {;
        @Value public static class Create implements SourceFileName, TargetFileName {
            String SourceFileName;
            String TargetFileName;
        }
    }

    public enum  RequestUploadFile {;
        @Value public static class Create implements TargetFileName, Bytes {
            String TargetFileName;
            byte[] Bytes;
        }
    }

    public enum  ResponseGetFileList {;
        @Value public static class Create implements FileList {
            List<?> FileList;
        }
    }

    public enum RequestDownloadFile {;
        @Value public static class Create implements SourceFileName {
            String SourceFileName;
        }
    }

    public enum ResponseDownloadFile {;
        @Value public static class Create implements FileResource {
            FileSystemResource FileResource;
        }
    }


}
