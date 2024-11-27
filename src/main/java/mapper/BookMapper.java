package mapper;

import model.Book;
import model.builder.BookBuilder;
import view.model.BookDTO;
import view.model.builder.BookDTOBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class BookMapper {

    public static BookDTO convertBookToBookDTO(Book book)
    {
        return new BookDTOBuilder().setId(book.getId()).setPrice(book.getPrice())
                .setStock(book.getStock()).setAuthor(book.getAuthor()).
                setTitle(book.getTitle()).build();
    }
    public static Book convertBookDTOToBook(BookDTO bookDTO)
    {
        return new BookBuilder().setTitle(bookDTO.getTitle())
                .setAuthor(bookDTO.getAuthor())
                .setPublishedDate(LocalDate.of(2000,1,1))
                .setStock(bookDTO.getStock()).setPrice(bookDTO.getPrice())
                .setId(bookDTO.getId()).build();
    }

    public static List<BookDTO> covertBookListToBookDTOList(List<Book>books)
    {
        return books.parallelStream().map(BookMapper::convertBookToBookDTO).collect(Collectors.toList());
    }

    public static List<Book> converBookDTOListToBookList(List<BookDTO>books)
    {
        return books.parallelStream().map(BookMapper::convertBookDTOToBook).collect(Collectors.toList());
    }
}
