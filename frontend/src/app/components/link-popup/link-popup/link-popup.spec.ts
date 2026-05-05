import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LinkPopup } from './link-popup';

describe('LinkPopup', () => {
    let component: LinkPopup;
    let fixture: ComponentFixture<LinkPopup>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [LinkPopup],
        }).compileComponents();

        fixture = TestBed.createComponent(LinkPopup);
        component = fixture.componentInstance;
        await fixture.whenStable();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
