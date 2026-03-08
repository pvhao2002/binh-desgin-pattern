package com.db.service.prototype;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Prototype: cloneable template for creating new semesters from an existing config.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SemesterTemplate implements Cloneable {
    private String semesterName;
    private String academicYear;

    @Override
    public SemesterTemplate clone() {
        try {
            return (SemesterTemplate) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public SemesterTemplate copy() {
        return new SemesterTemplate(this.semesterName, this.academicYear);
    }
}
