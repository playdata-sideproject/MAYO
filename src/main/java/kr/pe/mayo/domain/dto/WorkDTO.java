package kr.pe.mayo.domain.dto;

import kr.pe.mayo.domain.User;
import kr.pe.mayo.domain.Work;
import lombok.AllArgsConstructor;
import lombok.Data;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class WorkDTO {

    @Data
    @AllArgsConstructor
    public static class Upload {
        private String title;
        private User user;
        private String username;
        @Enumerated(EnumType.STRING)
        private Category category;
        private String content;

        public Work toEntity() {
            return Work.builder()
                    .title(title)
                    .userIdx(user)
                    .category(category)
                    .content(content)
                    .build();
        }

    }

    @Data
    @AllArgsConstructor
    public static class Preview {
        private Long idx;
        private String title;
        private User user;
        private String username;
        @Enumerated(EnumType.STRING)
        private Category category;
        private Long thumbnailId;

        public Preview(Work work) {
            this.idx = work.getIdx();

            if(!work.getWorkImgs().isEmpty()) {
                this.thumbnailId = work.getWorkImgs().get(0).getIdx();
            }

        }
    }

}