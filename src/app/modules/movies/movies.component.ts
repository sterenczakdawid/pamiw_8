import { Page } from './../../core/interfaces/page.interface';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Observable, map } from 'rxjs';
import { Director, Movie } from 'src/app/core/interfaces/movie.interface';
import { MovieService } from 'src/app/core/services/movie.service';
import { NgForm } from '@angular/forms';
import { DirectorService } from 'src/app/core/services/director.service';

@Component({
  selector: 'app-movies',
  templateUrl: './movies.component.html',
  styleUrls: ['./movies.component.scss'],
})
export class MoviesComponent implements OnInit {
  public editMovie: Movie;
  public deleteMovie: Movie;

  constructor(
    private movieService: MovieService,
    private directorService: DirectorService
  ) {}

  movies$: Observable<Page<Movie>>;
  directors$: Observable<Director[]>;

  ngOnInit(): void {
    this.movies$ = this.movieService
      .get()
      .pipe(map((response) => response.data));

    this.directors$ = this.directorService
      .getDirectors()
      .pipe(map((response) => response.data));
  }

  public goToPage(title?: string, pageNumber = 0): void {
    this.movies$ = this.movieService
      .get(title, pageNumber)
      .pipe(map((response) => response.data));
  }

  public onOpenModal(movie: Movie, mode: string): void {
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      button.setAttribute('data-target', '#addMovieModal');
    }
    if (mode === 'edit') {
      this.editMovie = movie;
      button.setAttribute('data-target', '#updateMovieModal');
    }
    if (mode === 'delete') {
      this.deleteMovie = movie;
      button.setAttribute('data-target', '#deleteMovieModal');
    }
    container.appendChild(button);
    button.click();
  }

  public onAddMovie(addForm: NgForm): void {
    document.getElementById('add-movie-form').click();
    this.movieService.addMovie(addForm.value).subscribe({
      next: (response: Movie) => {
        console.log(response);
        this.goToPage();
        addForm.reset();
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
        addForm.reset();
      },
    });
  }

  public onUpdateMovie(movie: Movie): void {
    this.movieService.updateMovie(movie).subscribe({
      next: (response: Movie) => {
        console.log(response);
        this.goToPage();
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      },
    });
  }

  public onDeleteMovie(movieId: number): void {
    this.movieService.deleteMovie(movieId).subscribe({
      next: () => {
        console.log('Movie deleted successfully');
        this.goToPage();
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      },
    });
  }
}
