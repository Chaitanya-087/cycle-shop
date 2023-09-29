import { Directive, Input, OnChanges, SimpleChanges, TemplateRef, ViewContainerRef } from '@angular/core';
import { Role } from './models/role';
import { AuthService } from './services/auth.service';

@Directive({
  selector: '[hasRoles]'
})
export class HasRoleDirective implements OnChanges {
  private roles: Role[] = [];

  @Input() set hasRoles(roles: Role[]) {
    this.roles = roles;
  }

  constructor(private templateRef: TemplateRef<unknown>, private viewContainer: ViewContainerRef, private authService: AuthService) { }
  ngOnChanges(): void {
    this.updateView();
  }

  updateView(): void {
    this.authService.isLoggedIn.subscribe((value: boolean) => {
      if (value) {
        this.authService.user.subscribe(user => {
          if (this.roles.includes(user.role)) {
            this.viewContainer.createEmbeddedView(this.templateRef);
          } else {
            this.viewContainer.clear();
          }
        });
      } else {
        this.viewContainer.clear();
      }
    });
  }
}
