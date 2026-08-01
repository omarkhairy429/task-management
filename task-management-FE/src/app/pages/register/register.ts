import { Component, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../core/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class RegisterComponent {
  username = '';
  email = '';
  password = '';
  errorMsg = signal<string | null>(null);

  constructor(
    private auth: AuthService,
    private router: Router,
  ) {}

  onSubmit() {
    this.errorMsg.set(null);
    this.auth.register(this.username, this.email, this.password).subscribe({
      next: () => this.router.navigate(['/tasks']),
      error: (err) => {
        console.log('Full HTTP Error:', err);

        let message = 'Registration failed. Please try again.';

        if (typeof err.error === 'string') {
          try {
            const parsed = JSON.parse(err.error);
            message = parsed.error || err.error;
          } catch {
            message = err.error;
          }
        } else if (err.error?.error) {
          message = err.error.error;
        }

        this.errorMsg.set(message);
      },
    });
  }
}
