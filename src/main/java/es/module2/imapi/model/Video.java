package es.module2.imapi.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor  
@Builder
public class Video {

    @NonNull
    private String name;

    @NonNull
    private MultipartFile video_parts;

    // public String getName() {
    //     return this.name;
    // }

    // public void setName(String name) {
    //     this.name = name;
    // }

    // public MultipartFile getVideo_parts() {
    //     return this.video_parts;
    // }

    // public void setVideo_parts(MultipartFile video_parts) {
    //     this.video_parts = video_parts;
    // }
    // @Override
    // public String toString() {
    //     return "{" +
    //         " name='" + getName() + "'" +
    //         ", video_parts='" + getVideo_parts() + "'" +
    //         "}";
    // }


}

