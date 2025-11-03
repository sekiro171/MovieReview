
package com.example.Group3.confict.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.Group3.confict.model.Movie;
import com.example.Group3.confict.repository.MovieRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public void run(String... args) throws Exception {
        // Chỉ thêm dữ liệu nếu database trống
        if (movieRepository.count() == 0) {
            System.out.println("Adding sample movie data...");

            // Movie 1: The Shawshank Redemption
            Movie movie1 = new Movie(
                "The Shawshank Redemption",
                "Drama",
                "Frank Darabont",
                1994,
                "Câu chuyện về Andy Dufresne, một nhân viên ngân hàng bị kết án tù chung thân vì tội giết vợ và người tình của vợ mình. Tại nhà tù Shawshank, anh kết bạn với Red và tìm cách tồn tại trong môi trường khắc nghiệt.",
                "https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_FMjpg_UX1000_.jpg",
                9.3
            );

            // Movie 2: The Godfather
            Movie movie2 = new Movie(
                "The Godfather",
                "Crime, Drama",
                "Francis Ford Coppola",
                1972,
                "Câu chuyện về gia đình mafia Corleone dưới sự lãnh đạo của Don Vito Corleone. Khi ông bị ám sát hụt, con trai út Michael phải bước vào thế giới tội phạm để bảo vệ gia đình.",
                "https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg",
                9.2
            );

            // Movie 3: The Dark Knight
            Movie movie3 = new Movie(
                "The Dark Knight",
                "Action, Crime, Drama",
                "Christopher Nolan",
                2008,
                "Batman phải đối mặt với kẻ thù nguy hiểm nhất - Joker, một tên tội phạm tâm thần muốn tạo ra sự hỗn loạn ở Gotham City. Cuộc chiến giữa công lý và hỗn loạn bắt đầu.",
                "https://m.media-amazon.com/images/M/MV5BMTMxNTMwODM0NF5BMl5BanBnXkFtZTcwODAyMTk2Mw@@._V1_FMjpg_UX1000_.jpg",
                9.0
            );

            // Movie 4: Inception
            Movie movie4 = new Movie(
                "Inception",
                "Action, Sci-Fi, Thriller",
                "Christopher Nolan",
                2010,
                "Dom Cobb là một tên trộm chuyên nghiệp, chuyên đánh cắp bí mật từ tiềm thức trong lúc người ta đang mơ. Anh nhận nhiệm vụ cuối cùng: không phải lấy đi một ý tưởng mà là cấy một ý tưởng vào tâm trí người khác.",
                "https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_.jpg",
                8.8
            );

            // Movie 5: Pulp Fiction
            Movie movie5 = new Movie(
                "Pulp Fiction",
                "Crime, Drama",
                "Quentin Tarantino",
                1994,
                "Phim kể về những câu chuyện đan xen nhau của các tên gangster, võ sĩ quyền anh, và một cặp cướp nhà hàng ở Los Angeles. Các câu chuyện được kể theo cách phi tuyến tính độc đáo.",
                "https://m.media-amazon.com/images/M/MV5BNGNhMDIzZTUtNTBlZi00MTRlLWFjM2ItYzViMjE3YzI5MjljXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg",
                8.9
            );

            // Movie 6: Forrest Gump
            Movie movie6 = new Movie(
                "Forrest Gump",
                "Drama, Romance",
                "Robert Zemeckis",
                1994,
                "Câu chuyện về Forrest Gump, một người đàn ông có IQ thấp nhưng có trái tim nhân hậu. Anh đã vô tình tham gia vào nhiều sự kiện lịch sử quan trọng của nước Mỹ trong khi theo đuổi tình yêu đích thực của mình.",
                "https://m.media-amazon.com/images/M/MV5BNWIwODRlZTUtY2U3ZS00Yzg1LWJhNzYtMmZiYmEyNmU1NjMzXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_.jpg",
                8.8
            );

            // Movie 7: The Matrix
            Movie movie7 = new Movie(
                "The Matrix",
                "Action, Sci-Fi",
                "The Wachowskis",
                1999,
                "Thomas Anderson, một lập trình viên máy tính, khám phá ra sự thật đằng sau thế giới: loài người đang sống trong một thực tại ảo do máy móc tạo ra. Anh gia nhập cuộc chiến để giải phóng nhân loại.",
                "https://m.media-amazon.com/images/M/MV5BNzQzOTk3OTAtNDQ0Zi00ZTVkLWI0MTEtMDllZjNkYzNjNTc4L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_.jpg",
                8.7
            );

            // Movie 8: Interstellar
            Movie movie8 = new Movie(
                "Interstellar",
                "Adventure, Drama, Sci-Fi",
                "Christopher Nolan",
                2014,
                "Trong tương lai gần, Trái Đất đang dần trở nên không thể sinh sống. Một nhóm các nhà du hành vũ trụ phải đi qua một lỗ sâu vũ trụ để tìm kiếm một hành tinh mới có thể cứu nhân loại.",
                "https://m.media-amazon.com/images/M/MV5BZjdkOTU3MDktN2IxOS00OGEyLWFmMjktY2FiMmZkNWIyODZiXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_.jpg",
                8.6
            );

            // Save all movies to database
            movieRepository.save(movie1);
            movieRepository.save(movie2);
            movieRepository.save(movie3);
            movieRepository.save(movie4);
            movieRepository.save(movie5);
            movieRepository.save(movie6);
            movieRepository.save(movie7);
            movieRepository.save(movie8);

            System.out.println("Successfully added " + movieRepository.count() + " sample movies to database!");
        } else {
            System.out.println("Database already has " + movieRepository.count() + " movies. Skipping sample data initialization.");
        }
    }

}