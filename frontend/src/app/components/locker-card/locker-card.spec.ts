import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LockerCard } from './locker-card';

describe('LockerCard', () => {
    let component: LockerCard;
    let fixture: ComponentFixture<LockerCard>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [LockerCard],
        }).compileComponents();

        fixture = TestBed.createComponent(LockerCard);
        component = fixture.componentInstance;
        await fixture.whenStable();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
