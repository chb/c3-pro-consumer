CREATE TABLE resource_table (
  uuid VARCHAR2(40) NOT NULL,
  json CLOB,
  key CLOB,
  key_id VARCHAR2(40) NOT NULL,
  processed VARCHAR2(700),
  start_date DATE,
  fhir_version VARCHAR2(40) NOT NULL
);

CREATE TABLE patient_map (
  id VARCHAR2(40) NOT NULL,
  signature VARCHAR2(40) NOT NULL,
  subject_id VARCHAR2(40),
  start_date DATE,
  system VARCHAR2(200),
  code VARCHAR2(200),
  display VARCHAR2(200),
  version_id VARCHAR2(200)
);

CREATE TABLE C3PRO (
  "C_HLEVEL"				NUMBER(22,0)	NOT NULL,
  "C_FULLNAME"			VARCHAR2(700)	NOT NULL,
  "C_NAME"				VARCHAR2(2000)	NOT NULL,
  "C_SYNONYM_CD"			CHAR(1)	NOT NULL,
  "C_VISUALATTRIBUTES"	CHAR(3)	NOT NULL,
  "C_TOTALNUM"			NUMBER(22,0)	NULL,
  "C_BASECODE"			VARCHAR2(50)	NULL,
  "C_METADATAXML"			CLOB	NULL,
  "C_FACTTABLECOLUMN"		VARCHAR2(50)	NOT NULL,
  "C_TABLENAME"			VARCHAR2(50)	NOT NULL,
  "C_COLUMNNAME"			VARCHAR2(50)	NOT NULL,
  "C_COLUMNDATATYPE"		VARCHAR2(50)	NOT NULL,
  "C_OPERATOR"			VARCHAR2(10)	NOT NULL,
  "C_DIMCODE"				VARCHAR2(700)	NOT NULL,
  "C_COMMENT"				CLOB	NULL,
  "C_TOOLTIP"				VARCHAR2(900)	NULL,
  "M_APPLIED_PATH"		VARCHAR2(700)	NOT NULL,
  "UPDATE_DATE"			DATE	NOT NULL,
  "DOWNLOAD_DATE"			DATE	NULL,
  "IMPORT_DATE"			DATE	NULL,
  "SOURCESYSTEM_CD"		VARCHAR2(50)	NULL,
  "VALUETYPE_CD"			VARCHAR2(50)	NULL,
  "M_EXCLUSION_CD"			VARCHAR2(25) NULL,
  "C_PATH"				VARCHAR2(700)   NULL,
  "C_SYMBOL"				VARCHAR2(50)	NULL
)
;
CREATE INDEX META_FULLNAME_C3PRO_IDX ON C3PRO(C_FULLNAME)
;
CREATE INDEX META_APPLIED_PATH_C3PRO_IDX ON C3PRO(M_APPLIED_PATH)
;
CREATE INDEX META_EXCLUSION_C3PRO_IDX ON C3PRO(M_EXCLUSION_CD)
;
CREATE INDEX META_HLEVEL_C3PRO_IDX ON C3PRO(C_HLEVEL)
;
CREATE INDEX META_SYNONYM_C3PRO_IDX ON C3PRO(C_SYNONYM_CD)
;

INSERT INTO TABLE_ACCESS(C_TABLE_CD, C_TABLE_NAME, C_PROTECTED_ACCESS, C_HLEVEL, C_FULLNAME, C_NAME, C_SYNONYM_CD, C_VISUALATTRIBUTES, C_TOTALNUM, C_BASECODE, C_METADATAXML, C_FACTTABLECOLUMN, C_DIMTABLENAME, C_COLUMNNAME, C_COLUMNDATATYPE, C_OPERATOR, C_DIMCODE, C_COMMENT, C_TOOLTIP, C_ENTRY_DATE, C_CHANGE_DATE, C_STATUS_CD, VALUETYPE_CD)
VALUES('c-tracker', 'C3PRO', 'N', 0, '\c-tracker\', 'c-tracker', 'N', 'CA', NULL, NULL, NULL, 'concept_cd', 'concept_dimension', 'concept_path', 'T', 'LIKE', '\c-tracker\', NULL, 'c-tracker', NULL, NULL, NULL, NULL);
