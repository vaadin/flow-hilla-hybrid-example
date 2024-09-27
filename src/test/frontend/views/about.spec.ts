import { test, expect } from '@playwright/test';

function getUrl() {
  return 'http://localhost:8080/about';
}

test.beforeEach(async ({ page }) => {
  await page.goto(getUrl());
  await page.getByLabel('Username').fill("user");
  await page.getByLabel('Password', { exact: true }).fill("user");
  await page.getByRole('button', { name: 'Log in' }).click();
  await page.waitForURL(new RegExp(`${getUrl()}(\\?continue)?`));
  await page.waitForTimeout(300);
});

test('should display a view for authenticated users', async ({ page }) => {
  await expect(await page.locator('p')).toContainText('This is a Hilla view available for authenticated users.');
});

