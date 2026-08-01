// src/app/pages/register/register.component.ts
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
      error: () => this.errorMsg.set('Registration failed — username may already exist.'),
    });
  }
}
