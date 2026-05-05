import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LockerSelection } from './locker-selection';

describe('LockerSelection', () => {
    let component: LockerSelection;
    let fixture: ComponentFixture<LockerSelection>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [LockerSelection],
        }).compileComponents();

        fixture = TestBed.createComponent(LockerSelection);
        component = fixture.componentInstance;
        await fixture.whenStable();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
