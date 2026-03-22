INSERT INTO service_categories (name) VALUES
('Development & IT'),
('Design & Creative'),
('Writing & Content'),
('Marketing & Sales'),
('Video, Audio & Media'),
('Business & Consulting'),
('Data, AI & Automation'),
('E-commerce'),
('Other Services');

INSERT INTO service_subcategories (name, category_id) VALUES
('Web Development',        (SELECT id FROM service_categories WHERE name = 'Development & IT')),
('Frontend Development',  (SELECT id FROM service_categories WHERE name = 'Development & IT')),
('Backend Development',   (SELECT id FROM service_categories WHERE name = 'Development & IT')),
('Full-Stack Development',(SELECT id FROM service_categories WHERE name = 'Development & IT')),
('WordPress Development', (SELECT id FROM service_categories WHERE name = 'Development & IT')),
('Mobile Development',    (SELECT id FROM service_categories WHERE name = 'Development & IT')),
('Game Development',      (SELECT id FROM service_categories WHERE name = 'Development & IT')),
('QA & Testing',          (SELECT id FROM service_categories WHERE name = 'Development & IT')),
('DevOps & Cloud',        (SELECT id FROM service_categories WHERE name = 'Development & IT')),
('Databases',             (SELECT id FROM service_categories WHERE name = 'Development & IT'));

INSERT INTO service_subcategories (name, category_id) VALUES
('Graphic Design',     (SELECT id FROM service_categories WHERE name = 'Design & Creative')),
('UI/UX Design',       (SELECT id FROM service_categories WHERE name = 'Design & Creative')),
('Web Design',         (SELECT id FROM service_categories WHERE name = 'Design & Creative')),
('Illustration',       (SELECT id FROM service_categories WHERE name = 'Design & Creative')),
('Motion & Video',     (SELECT id FROM service_categories WHERE name = 'Design & Creative'));

INSERT INTO service_subcategories (name, category_id) VALUES
('Copywriting',        (SELECT id FROM service_categories WHERE name = 'Writing & Content')),
('Content Writing',    (SELECT id FROM service_categories WHERE name = 'Writing & Content')),
('Translation',        (SELECT id FROM service_categories WHERE name = 'Writing & Content')),
('Proofreading',       (SELECT id FROM service_categories WHERE name = 'Writing & Content')),
('Scriptwriting',      (SELECT id FROM service_categories WHERE name = 'Writing & Content'));

INSERT INTO service_subcategories (name, category_id) VALUES
('Digital Marketing',  (SELECT id FROM service_categories WHERE name = 'Marketing & Sales')),
('Advertising',        (SELECT id FROM service_categories WHERE name = 'Marketing & Sales')),
('SEO',                (SELECT id FROM service_categories WHERE name = 'Marketing & Sales')),
('Analytics & CRO',    (SELECT id FROM service_categories WHERE name = 'Marketing & Sales')),
('CRM & Sales Funnels',(SELECT id FROM service_categories WHERE name = 'Marketing & Sales'));

INSERT INTO service_subcategories (name, category_id) VALUES
('Video Production',   (SELECT id FROM service_categories WHERE name = 'Video, Audio & Media')),
('Audio Services',     (SELECT id FROM service_categories WHERE name = 'Video, Audio & Media')),
('Photography',        (SELECT id FROM service_categories WHERE name = 'Video, Audio & Media'));

INSERT INTO service_subcategories (name, category_id) VALUES
('Business Consulting',(SELECT id FROM service_categories WHERE name = 'Business & Consulting')),
('Finance & Accounting',(SELECT id FROM service_categories WHERE name = 'Business & Consulting')),
('Legal Services',     (SELECT id FROM service_categories WHERE name = 'Business & Consulting')),
('Project Management', (SELECT id FROM service_categories WHERE name = 'Business & Consulting')),
('HR & Recruitment',   (SELECT id FROM service_categories WHERE name = 'Business & Consulting'));

INSERT INTO service_subcategories (name, category_id) VALUES
('Data Analysis',      (SELECT id FROM service_categories WHERE name = 'Data, AI & Automation')),
('Machine Learning',   (SELECT id FROM service_categories WHERE name = 'Data, AI & Automation')),
('AI Services',        (SELECT id FROM service_categories WHERE name = 'Data, AI & Automation')),
('Automation',         (SELECT id FROM service_categories WHERE name = 'Data, AI & Automation'));

INSERT INTO service_subcategories (name, category_id) VALUES
('Online Stores',      (SELECT id FROM service_categories WHERE name = 'E-commerce')),
('Product Management', (SELECT id FROM service_categories WHERE name = 'E-commerce')),
('Marketplace Setup',  (SELECT id FROM service_categories WHERE name = 'E-commerce')),
('Payment Integration',(SELECT id FROM service_categories WHERE name = 'E-commerce'));

INSERT INTO service_subcategories (name, category_id) VALUES
('Virtual Assistant',  (SELECT id FROM service_categories WHERE name = 'Other Services')),
('Customer Support',   (SELECT id FROM service_categories WHERE name = 'Other Services')),
('Data Entry',         (SELECT id FROM service_categories WHERE name = 'Other Services')),
('Research',           (SELECT id FROM service_categories WHERE name = 'Other Services')),
('Other',              (SELECT id FROM service_categories WHERE name = 'Other Services'));
