import { Component, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../core/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class LoginComponent {
  username = '';
  password = '';
  errorMsg = signal<string | null>(null);

  constructor(
    private auth: AuthService,
    private router: Router,
  ) {}

  onSubmit() {
    this.errorMsg.set(null);
    this.auth.login(this.username, this.password).subscribe({
      next: () => this.router.navigate(['/tasks']),
      error: () => this.errorMsg.set('Invalid username or password.'),
    });
  }
}
